/* Input.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 12 $
 * Author: $Author: miha_vitorovic $
 * Date: $Date: 2007-05-11 20:49:09 +0900 (金, 11  5月 2007) $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Handles input events and sends relevant input data to server
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 * 
 * (See gpl.txt for details of the GNU General Public License.)
 * 
 */

package net.propero.rdp;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import net.propero.rdp.keymapping.KeyCode;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Input {

	protected static Logger logger = Logger.getLogger(Input.class.getName());

	protected Vector pressedKeys;

	protected static boolean capsLockOn = false;

	protected static boolean numLockOn = false;

	protected static boolean scrollLockOn = false;

	protected static boolean serverAltDown = false;

	protected static boolean altDown = false;

	protected static boolean ctrlDown = false;

	protected static long last_mousemove = 0;

	// Using this flag value (0x0001) seems to do nothing, and after running
	// through other possible values, the RIGHT flag does not appear to be
	// implemented
	protected static final int KBD_FLAG_RIGHT = 0x0001;

	protected static final int KBD_FLAG_EXT = 0x0100;

	// QUIET flag is actually as below (not 0x1000 as in rdesktop)
	protected static final int KBD_FLAG_QUIET = 0x200;

	protected static final int KBD_FLAG_DOWN = 0x4000;

	protected static final int KBD_FLAG_UP = 0x8000;

	protected static final int RDP_KEYPRESS = 0;

	protected static final int RDP_KEYRELEASE = KBD_FLAG_DOWN | KBD_FLAG_UP;

	protected static final int MOUSE_FLAG_MOVE = 0x0800;

	protected static final int MOUSE_FLAG_BUTTON1 = 0x1000;

	protected static final int MOUSE_FLAG_BUTTON2 = 0x2000;

	protected static final int MOUSE_FLAG_BUTTON3 = 0x4000;

	protected static final int MOUSE_FLAG_BUTTON4 = 0x0280; // wheel up -

	// rdesktop 1.2.0
	protected static final int MOUSE_FLAG_BUTTON5 = 0x0380; // wheel down -

	// rdesktop 1.2.0
	protected static final int MOUSE_FLAG_DOWN = 0x8000;

	protected static final int RDP_INPUT_SYNCHRONIZE = 0;

	protected static final int RDP_INPUT_CODEPOINT = 1;

	protected static final int RDP_INPUT_VIRTKEY = 2;

	protected static final int RDP_INPUT_SCANCODE = 4;

	protected static final int RDP_INPUT_MOUSE = 0x8001;

	protected static int time = 0;

	public boolean modifiersValid = false;

	public boolean keyDownWindows = false;

	protected Rdp rdp = null;

	KeyCode keys = null;

	/**
	 * Create a new Input object with a given keymap object
	 * 
	 * @param r
	 *            Rdp layer on which to send input messages
	 */
	public Input(Rdp r) {
		rdp = r;
		pressedKeys = new Vector();
	}

	/**
	 * Retrieve the next "timestamp", by incrementing previous stamp (up to the
	 * maximum value of an integer, at which the timestamp is reverted to 1)
	 * 
	 * @return New timestamp value
	 */
	public static int getTime() {
		time++;
		if (time == Integer.MAX_VALUE)
			time = 1;
		return time;
	}

	/**
	 * Send a keyboard event to the server
	 * 
	 * @param time
	 *            Time stamp to identify this event
	 * @param flags
	 *            Flags defining the nature of the event (eg:
	 *            press/release/quiet/extended)
	 * @param scancode
	 *            Scancode value identifying the key in question
	 * @throws Exception 
	 */
	public void sendScancode(long time, int flags, int scancode) throws Exception {

		if (scancode == 0x38) { // be careful with alt
			if ((flags & RDP_KEYRELEASE) != 0) {
				// logger.info("Alt release, serverAltDown = " + serverAltDown);
				serverAltDown = false;
			}
			if ((flags == RDP_KEYPRESS)) {
				// logger.info("Alt press, serverAltDown = " + serverAltDown);
				serverAltDown = true;
			}
		}

		if ((scancode & KeyCode.SCANCODE_EXTENDED) != 0) {
			rdp.sendInput((int) time, RDP_INPUT_SCANCODE, flags | KBD_FLAG_EXT,
					scancode & ~KeyCode.SCANCODE_EXTENDED, 0);
		} else
			rdp.sendInput((int) time, RDP_INPUT_SCANCODE, flags, scancode, 0);
	}

	/**
	 * Turn off any locking key, check states if available
	 */
	public void triggerReadyToSend() {
		capsLockOn = false;
		numLockOn = false;
		scrollLockOn = false;
		doLockKeys(); // ensure lock key states are correct
	}

	protected void doLockKeys() {
	}

	private void pressKey( long time, int scancode ) throws Exception{
		sendScancode(time, RDP_KEYPRESS, scancode);
	}
	
	private void upKey( long time, int scancode ) throws Exception{
		sendScancode(time, RDP_KEYRELEASE, scancode);
	}
	
	public void pushKey(int modifier, int scancode) throws Exception{
		time = getTime();
		if( modifier != 0 ){
			pressKey(time, modifier);
		}
		
		pressKey(time, scancode);
		
		time = getTime();
		
		if( modifier != 0 ){
			upKey(time, modifier);
		}
		
		upKey(time, scancode);
		
	}
	
	public void pushKey(int scancode) throws Exception{
		time = getTime();
		pressKey(time, scancode);
		upKey(time, scancode);
	}
	
	public void pushKeys(String phrase) throws Exception{
		List<String> keys = Arrays.asList(KeyCode.main_key_US);
		for (char chr: phrase.toCharArray()) {
			int pos = keys.indexOf(chr);
			if(pos > -1){
				 pushKey( KeyCode.main_key_scan_qwerty[ pos ] );
			}
		} 
	}
}
