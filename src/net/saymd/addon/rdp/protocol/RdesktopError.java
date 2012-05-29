/* RdesktopError.java ( copied from net.propero.rdp.Rdesktop.java )
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

public class RdesktopError {
	private final int exDiscReasonNoInfo = 0x0000;

	private final int exDiscReasonAPIInitiatedDisconnect = 0x0001;

	private final int exDiscReasonAPIInitiatedLogoff = 0x0002;

	private final int exDiscReasonServerIdleTimeout = 0x0003;

	private final int exDiscReasonServerLogonTimeout = 0x0004;

	private final int exDiscReasonReplacedByOtherConnection = 0x0005;

	private final int exDiscReasonOutOfMemory = 0x0006;

	private final int exDiscReasonServerDeniedConnection = 0x0007;

	private final int exDiscReasonServerDeniedConnectionFips = 0x0008;

	private final int exDiscReasonLicenseInternal = 0x0100;

	private final int exDiscReasonLicenseNoLicenseServer = 0x0101;

	private final int exDiscReasonLicenseNoLicense = 0x0102;

	private final int exDiscReasonLicenseErrClientMsg = 0x0103;

	private final int exDiscReasonLicenseHwidDoesntMatchLicense = 0x0104;

	private final int exDiscReasonLicenseErrClientLicense = 0x0105;

	private final int exDiscReasonLicenseCantFinishProtocol = 0x0106;

	private final int exDiscReasonLicenseClientEndedProtocol = 0x0107;

	private final int exDiscReasonLicenseErrClientEncryption = 0x0108;

	private final int exDiscReasonLicenseCantUpgradeLicense = 0x0109;

	private final int exDiscReasonLicenseNoRemoteConnections = 0x010a;
	
	private int reason;
	
	public RdesktopError(int reason){
		this.reason = reason;
	}
	
	public String toString() {
		String text;

		switch (this.reason) {
		case exDiscReasonNoInfo:
			text = "No information available";
			break;

		case exDiscReasonAPIInitiatedDisconnect:
			text = "Server initiated disconnect";
			break;

		case exDiscReasonAPIInitiatedLogoff:
			text = "Server initiated logoff";
			break;

		case exDiscReasonServerIdleTimeout:
			text = "Server idle timeout reached";
			break;

		case exDiscReasonServerLogonTimeout:
			text = "Server logon timeout reached";
			break;

		case exDiscReasonReplacedByOtherConnection:
			text = "Another user connected to the session";
			break;

		case exDiscReasonOutOfMemory:
			text = "The server is out of memory";
			break;

		case exDiscReasonServerDeniedConnection:
			text = "The server denied the connection";
			break;

		case exDiscReasonServerDeniedConnectionFips:
			text = "The server denied the connection for security reason";
			break;

		case exDiscReasonLicenseInternal:
			text = "Internal licensing error";
			break;

		case exDiscReasonLicenseNoLicenseServer:
			text = "No license server available";
			break;

		case exDiscReasonLicenseNoLicense:
			text = "No valid license available";
			break;

		case exDiscReasonLicenseErrClientMsg:
			text = "Invalid licensing message";
			break;

		case exDiscReasonLicenseHwidDoesntMatchLicense:
			text = "Hardware id doesn't match software license";
			break;

		case exDiscReasonLicenseErrClientLicense:
			text = "Client license error";
			break;

		case exDiscReasonLicenseCantFinishProtocol:
			text = "Network error during licensing protocol";
			break;

		case exDiscReasonLicenseClientEndedProtocol:
			text = "Licensing protocol was not completed";
			break;

		case exDiscReasonLicenseErrClientEncryption:
			text = "Incorrect client license enryption";
			break;

		case exDiscReasonLicenseCantUpgradeLicense:
			text = "Can't upgrade license";
			break;

		case exDiscReasonLicenseNoRemoteConnections:
			text = "The server is not licensed to accept remote connections";
			break;

		default:
			if (reason > 0x1000 && reason < 0x7fff) {
				text = "Internal protocol error";
			} else {
				text = "Unknown reason";
			}
		}
		return text;
	}
}