/* SessionManager.java
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

import java.util.HashMap;

import net.propero.rdp.Options;

public class SessionManager {
	private static SessionManager mngr;
	private HashMap<Long, RdesktopSession> sessions;
	
	private SessionManager(){
		sessions = new HashMap<Long, RdesktopSession>();
	}
	
	/**
	 * get instance of SessionManager.
	 * @return Returns this class instance
	 */
	public static SessionManager getInstance(){
		if(mngr == null){
			mngr = new SessionManager();
		}
		
		return mngr;
	}
	
	/**
	 * bind session to session manager
	 * @param session target session instance to bind
	 */
	public void bind( RdesktopSession session ){
		sessions.put( session.id , session );
	}
	
	/**
	 * unbind session from session manager
	 * @param sessionId session-id to unbind
	 */
	public void unbind( long sessionId ){
		if( sessions.containsKey(sessionId) ){
			sessions.remove(sessionId);
		}
	}
	
	/**
	 * get session
	 * @param sessionId session-id
	 * @return Returns the session instance, or null if requested session dose not exist.
	 */
	public RdesktopSession get( long sessionId ){
		return sessions.get(sessionId);
	}
	
	/**
	 * tell exit request to session
	 * @param sessionId target session id to tell exit request
	 */
	public void exitSession(long sessionId){
		sessions.get(sessionId).exit();
	}
	
	/**
	 * get session option parameter
	 * @param sessionId  target session id to get
	 * @return Returns session option parameter, or null if requested session dose not exist.
	 */
	public Options getOption(long sessionId){
		return sessions.containsKey(sessionId) ? sessions.get(sessionId).getOption() : null;
	}
}