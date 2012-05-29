/* Bitmap.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 12 $
 * Author: $Author: miha_vitorovic $
 * Date: $Date: 2007-05-11 20:49:09 +0900 (金, 11  5月 2007) $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Provide a class for storage of Bitmap images, along with
 *          static methods for decompression and conversion of bitmaps.
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

import org.apache.log4j.Logger;

public class Bitmap {
	
	/**
	 * Constructor for Bitmap based on integer pixel values
	 * 
	 * @param data
	 *            Array of pixel data, one integer per pixel. Should have a
	 *            length of width*height.
	 * @param width
	 *            Width of bitmap represented by data
	 * @param height
	 *            Height of bitmap represented by data
	 * @param x
	 *            Desired x-coordinate of bitmap
	 * @param y
	 *            Desired y-coordinate of bitmap
	 */
	public Bitmap(int[] data, int width, int height, int x, int y) {}

	/**
	 * Constructor for Bitmap based on
	 * 
	 * @param data
	 *            Array of pixel data, each pixel represented by Bpp bytes.
	 *            Should have a length of width*height*Bpp.
	 * @param width
	 *            Width of bitmap represented by data
	 * @param height
	 *            Height of bitmap represented by data
	 * @param x
	 *            Desired x-coordinate of bitmap
	 * @param y
	 *            Desired y-coordinate of bitmap
	 * @param Bpp
	 *            Number of bytes per pixel in image represented by data
	 */
	public Bitmap(byte[] data, int width, int height, int x, int y, int Bpp) {}
}
