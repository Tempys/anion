package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStream;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamByteEncoder {
	private BitOutputStream bs;
	private int padding = 0;
	private int lastByte = 0;
	

	public BitStreamByteEncoder(BitOutputStream bs){
		this.bs = bs;
	}
	/**
	 * writes an byte to {@link BitOutputStream}, considering padding 
	 * @param data
	 */
	public void writeByte(int data){
		writeByte(data, 8);
	}
	/**
	 * writes an bitcount (max 8) to {@link BitOutputStream}, considering padding 
	 * @param data
	 * @param bitCount
	 */
	public void writeByte(int data, int bitCount){
		if (bitCount > 8){
			bitCount = 8;
		}
		if (padding == 0 && bitCount == 8) {
			bs.writeByte(data);
		} else if (padding == 0){
			lastByte = data;
			padding = bitCount;
		} else {
			if (bitCount + padding <= 8) {
				lastByte = (data << padding) | (BitTools.bitMask[padding] & lastByte);
				padding += bitCount;
				if (padding == 8) {
					padding = 0;
					bs.writeByte(lastByte);
					lastByte = 0;
				}
				return;
			} else {
				int firstBitCount = 8 - padding;
				try{
					bs.writeByte(lastByte | ((data & BitTools.bitMask[firstBitCount]) << padding));
				} finally {
					padding = bitCount - firstBitCount;
					lastByte = data >>> firstBitCount;	
				}
			}
		}
	}
	/**
	 * writes last bits to byte
	 */
	public void pad(){
		pad(false);
	}
	/**
	 * writes last bits to byte
	 * @param one - writes 1 if true
	 * 
	 */
	public void pad(boolean one){
		writeByte(one ? 1:0, 8 - padding);
	}
}
