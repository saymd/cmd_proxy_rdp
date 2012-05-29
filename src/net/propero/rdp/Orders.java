/* Orders.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 13 $
 * Author: $Author: miha_vitorovic $
 * Date: $Date: 2007-05-11 21:14:45 +0900 (金, 11  5月 2007) $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Encapsulates an RDP order
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

public class Orders {
	public void resetOrderState(){};

	/**
	 * Process a set of orders sent by the server
	 * 
	 * @param data
	 *            Packet packet containing orders
	 * @param next_packet
	 *            Offset of end of this packet (start of next)
	 * @param n_orders
	 *            Number of orders sent in this packet
	 * @throws OrderException
	 * @throws RdesktopException
	 */
	public void processOrders(RdpPacket_Localised data, int next_packet,
			int n_orders) throws OrderException, RdesktopException{};

	/**
	 * Register an RdesktopCanvas with this Orders object. This surface is where
	 * all drawing orders will be carried out.
	 * 
	 * @param surface
	 *            Surface to register
	 */
	//public void registerDrawingSurface(RdesktopCanvas surface){};

	/**
	 * Set current cache
	 * 
	 * @param cache
	 *            Cache object to set as current global cache
	 */
	public void registerCache(Cache cache){};
}
