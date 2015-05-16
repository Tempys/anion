package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;

public class BitStreamByteDecoder {
	private final BitInputStream bs;
	private int lastByte = 0;
	private int padding = 0;
	int[] bitMask = new int[] { 0b00000000, 0b00000001, 0b00000011, 0b00000111,
			0b00001111, 0b00011111, 0b00111111, 0b01111111, 0b11111111 };

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
		//
		// TODO: optimize this
		//
		if (bitCount > 8){
			bitCount = 8;
		}
		if (padding == 0 && bitCount == 8) {
			return bs.loadByte();
		} else if (padding == 0) {
			int loadedByte = bs.loadByte();
			int data = loadedByte & bitMask[bitCount];
			lastByte = loadedByte;
			padding = bitCount;
			return data;
		} else {
			int data = lastByte >>> padding;
			if (bitCount + padding <= 8) {
				padding += bitCount;
				if (padding == 8) {
					padding = 0;
				}
				return data & bitMask[bitCount];
			} else {
				int lastByte = bs.loadByte();
				int firstBitCount = 8 - padding;
				int secondBitCount = bitCount - firstBitCount;
				data = data << secondBitCount;
				data = data | (lastByte & bitMask[secondBitCount]);
				padding = (bitCount - 8);
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
}
