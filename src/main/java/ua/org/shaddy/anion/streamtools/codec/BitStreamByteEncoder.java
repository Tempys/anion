package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStream;
import ua.org.shaddy.anion.tools.BitStreamException;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamByteEncoder {
	private final BitOutputStream bs;
	private int padding = 0;
	private int lastByte = 0;
	

	public BitStreamByteEncoder(BitOutputStream bs){
		this.bs = bs;
	}
	
	public BitOutputStream getBs() {
		return bs;
	}

	/**
	 * writes an byte to {@link BitOutputStream}, considering padding 
	 * @param data
	 */
	public void writeByte(int data){
		writeByte(data, 8);
	}
	/**
	 * writes an bitCount (max 8) to {@link BitOutputStream}, considering padding 
	 * @param data
	 * @param bitCount
	 */
	public void writeByte(int data, int bitCount){
		if (bitCount > 8){
			throw new BitStreamException("Error, bit count is more than 8:" + bitCount);
		}
		if (padding == 0) {
			if (bitCount == 8){
				bs.writeByte(data);	
			} else {
				//
				//		10101010
				//		^ ^
				//		|_| 3 bits
				lastByte = (data << (8 - bitCount)) & BitTools.invertedBackBitMask[bitCount] ;
				padding = bitCount;
			}			
		} else {
			int countAndPadding = bitCount + padding; 
			if (countAndPadding <= 8) {
				lastByte = (data << (8 - countAndPadding)) | lastByte;
				padding += bitCount;
				if (padding == 8) {
					padding = 0;
					bs.writeByte(lastByte);
				}
			} else {
				int firstBitCount = 8 - padding;
				try{
					bs.writeByte(lastByte | (data  >>> padding));
				} finally {
					padding = bitCount - firstBitCount;
					lastByte = (data & BitTools.bitMask[padding]) << firstBitCount;	
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
		writeByte(one ? 255:0, 8 - padding);
	}
}
