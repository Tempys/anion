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
	public void saveByte(int data){
		saveByte(data, 8);
	}
	public void saveByte(int data, int bitCount){
		if (bitCount > 8){
			bitCount = 8;
		}
		if (padding == 0 && bitCount == 8) {
			bs.saveByte(data);
		} else if (padding == 0){
			lastByte = data;
			padding = bitCount;
		} else {
			if (bitCount + padding <= 8) {
				lastByte = (data << padding) | lastByte;
				padding += bitCount;
				if (padding == 8) {
					padding = 0;
					bs.saveByte(lastByte);
					lastByte = 0;
				}
				return;
			} else {
				int firstBitCount = 8 - padding;
				try{
					bs.saveByte(lastByte | ((data & BitTools.bitMask[firstBitCount]) << padding));
				} finally {
					padding = bitCount - firstBitCount;
					lastByte = data >>> firstBitCount;	
				}
			}
		}
	}
}
