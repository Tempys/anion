package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.tools.BitStreamException;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamByteDecoder {
	private final BitInputStream bs;
	private int lastByte = 0;
	private int padding = 0;

	public BitStreamByteDecoder(BitInputStream bs) {
		this.bs = bs;
	}
	/**
	 * loads an 8 bit byte from {@link BitInputStream}
	 * @return
	 */
	public int loadByte() {
		return loadByte(8);
	}
	/**
	 * loads number of bits from {@link BitInputStream}
	 * @param bitCount
	 * @return
	 */
	public int loadByte(int bitCount) {
		if (bitCount > 8){
			throw new BitStreamException("Error, bit count is more than 8:" + bitCount);
		}
		if (padding == 0 && bitCount == 8) {
			return bs.loadByte();
		} else if (padding == 0) {
			int loadedByte = bs.loadByte();
			int data = (loadedByte >>> (8 - bitCount)) & BitTools.bitMask[bitCount] ;
			padding = bitCount;
			lastByte = loadedByte;
			return data;
		} else {
			int countAndPadding = bitCount + padding; 
			if ( countAndPadding <= 8 ) {
				int data = (lastByte >>> (8 - countAndPadding)) & BitTools.bitMask[bitCount];
				padding += bitCount;
				if (padding == 8) {
					padding = 0;
				}
				return data;
			} else {
				int lowBitCount = countAndPadding - 8;
				int data = (lastByte & BitTools.backBitMask[padding]) << lowBitCount;
				lastByte = bs.loadByte();
				data = data | ((lastByte & BitTools.backBitMask[lowBitCount]) >>> (8 - lowBitCount));				
				padding = lowBitCount;
				return data;
			}
		}
	}
	/**
	 * returns padding (count of bits that already used in previous loads) 
	 * @return
	 */
	public int getPadding() {
		return padding;
	}
	
	public void skip(int skipSize) {
		bs.skip(skipSize);
	}
}
