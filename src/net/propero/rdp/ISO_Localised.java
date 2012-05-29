/* ISO.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 12 $
 * Author: $Author: miha_vitorovic $
 * Date: $Date: 2007-05-11 20:49:09 +0900 (金, 11  5月 2007) $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.4 specific extension of ISO class
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
/**
 * Created on 05-Aug-2003
 * 
 */

package net.propero.rdp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ISO_Localised extends ISO {
	public ISO_Localised(long sessionId) {
		super(sessionId);
	}

	protected void doSocketConnect(InetAddress host, int port)
			throws IOException {
		int timeout_ms = 3000; // timeout in milliseconds

		rdpsock = new Socket();
		rdpsock.connect(new InetSocketAddress(host, port), timeout_ms);
	}

}
