/* RdesktopSession.java ( copied from net.propero.rdp.Rdesktop.java )
 * RemoteDesktop Proxy Service                                                                                                                                                      
 * Copyright (C) 2012  saymd                                                                                                                       
 *                                                                                                                                                                                       
 * This application is free software; you can redistribute it and/or                                                                                                                         
 * modify it under the terms of the GNU General Public                                                                                                                            
 * License as published by the Free Software Foundation; either                                                                                                                          
 * version 3.0 of the License, or (at your option) any later version.                                                                                                                    
 *                                                                                                                                                                                       
 * This application is distributed in the hope that it will be useful,                                                                                                                       
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                                                                                                        
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU                                                                                                                     
 * Lesser General Public License for more details.                                                                                                                                       
 *                                                                                                                                                                                       
 * You should have received a copy of the GNU General Public                                                                                                                      
 * License along with this application; if not, write to the Free Software                                                                                                                   
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA                                                                                                             
 */
package net.saymd.addon.rdp.protocol;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.propero.rdp.ConnectionException;
import net.propero.rdp.Input;
import net.propero.rdp.MCS;
import net.propero.rdp.Options;
import net.propero.rdp.RdesktopException;
import net.propero.rdp.Rdp;
import net.propero.rdp.Secure;
import net.propero.rdp.keymapping.KeyCode;
import net.propero.rdp.rdp5.Rdp5;
import net.propero.rdp.rdp5.VChannels;
import net.saymd.addon.WindowsMachine;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class RdesktopSession {
	private Logger logger = Logger.getLogger(RdesktopSession.class.getName());
	private boolean keep_running;
	public final long id = System.currentTimeMillis();

	private Options option;
	private String server;
	public Rdp5 rdp;
	public Secure secure;
	public MCS mcs;
	
	/**
	 * initialize session related fields and construct session option parameter
	 * @param machine target machine to communicate
	 * @param command command to send to the machine
	 */
	public RdesktopSession(WindowsMachine machine,String command) {
		option = new Options();

		server = machine.host;
		option.domain = machine.domain == null ? "" : machine.domain;
		option.port = machine.port == 0 ? option.port : machine.port;
		option.username = machine.username;
		option.password = machine.password;
		option.command = command;
		
		SessionManager.getInstance().bind(this);
	}

	/**
	 * get session option parameter
	 * @return  Returns session option parameter.
	 */
	public Options getOption() {
		return option;
	}

	/**
	 * exit this session
	 */
	public void exit() {
		exit(rdp);
	}

	/**
	 * execute session
	 * @throws Exception
	 */
	public void execute() throws Exception {
		keep_running = true;
		logger.setLevel(Level.INFO);

		int logonflags = Rdp.RDP_LOGON_NORMAL;
		logonflags |= Rdp.RDP_LOGON_AUTO;

		VChannels channels = new VChannels(id);

		rdp = null;

		boolean[] deactivated = new boolean[1];
		int[] ext_disc_reason = new int[1];

		logger.debug("keep_running = " + keep_running);
		while (keep_running) {
			logger.debug("Initialising RDP layer...");
			rdp = new Rdp5(channels);

			if (rdp != null) {
				// Attempt to connect to server on port Options.port
				try {
					rdp.connect(option.username, InetAddress.getByName(server),
							logonflags, option.domain, option.password,
							option.command, option.directory);

					if (keep_running) {

						logger.info("Connection successful");
						rdp.mainLoop(deactivated, ext_disc_reason);

						if (deactivated[0]) {
							//clean disconnect
							exit(rdp);
						} else {
							String reason = new RdesktopError(
									ext_disc_reason[0]).toString();
							
							logger.warn("Connection terminated: " + reason);
							exit(rdp);
						}

						keep_running = false; // exited main loop
					} // closing bracket to if(running)

				} catch (ConnectionException e) {
					exit(rdp);
				} catch (UnknownHostException e) {
					error(e, rdp, true);
				} catch (SocketException s) {
					if (rdp.isConnected()) {
						logger.fatal(s.getClass().getName() + " "
								+ s.getMessage());
						
						error(s, rdp, true);
					}
				} catch (RdesktopException e) {
					String msg1 = e.getClass().getName();
					String msg2 = e.getMessage();
					logger.fatal(msg1 + ": " + msg2);

					error(e, rdp, true);

				} catch (Exception e) {
					logger.warn(e.getClass().getName() + " " + e.getMessage());
					error(e, rdp, true);
				}
			} else { // closing bracket to if(!rdp==null)
				logger.fatal("The communications layer could not be initiated!");
			}
		}
		exit(rdp);
	}

	/**
	 * Displays details of the Exception e in an error dialog via the
	 * RdesktopFrame window and reports this through the logger, then prints a
	 * stack trace.
	 * <p>
	 * The application then exits iff sysexit == true
	 * 
	 * @param e
	 * @param RdpLayer
	 * @param sysexit
	 */
	public void error(Exception e, Rdp RdpLayer, boolean sysexit) throws Exception {
		String msg1 = e.getClass().getName();
		String msg2 = e.getMessage();
		logger.fatal(msg1 + ": " + msg2);
		
		exit(RdpLayer);
		throw e;
	}

	/**
	 * dispatch requested command as post log-on action
	 * @throws Exception
	 */
	public void onLoggedOn() throws Exception {
		try {
			Thread.sleep(5000); // waits for initialization of screen
		} catch (InterruptedException e) {}
		
		Input input = new Input(rdp);
		input.pushKey(KeyCode.SCANCODE_EXTENDED | 0xe05b, 0x13); //press Win+R
		input.pushKeys( option.command ); //fill-in command input field
		input.pushKey(0x1c); //press Enter

		keep_running = false;
	}

	/**
	 * release rdp connection
	 * @param rdp current connection
	 */
	public void exit(Rdp rdp) {
		keep_running = false;

		if (rdp != null && rdp.isConnected()) {
			logger.info("Disconnecting ...");
			rdp.disconnect();
			logger.info("Disconnected");
		}
		
		System.gc();
	}
}
