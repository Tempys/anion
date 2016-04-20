package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStream;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamEncoder extends BitStreamByteEncoder{
	private byte byteOrder;
	/**
	 * By default byteOrder is LITTLE_ENDIAN
	 * @param bs
	 */
	public BitStreamEncoder(BitOutputStream bs) {
		this(bs, ByteOrder.BIG_ENDIAN);
	}
	
	public BitStreamEncoder(BitOutputStream bs, byte byteOrder) {
		super(bs);
		this.byteOrder = byteOrder;
	}
	/**
	 * function writes number of bits to a stream
	 * @param data
	 * @param size
	 */
	public void writeBits(long data, int size){
		while (size > 0) {
			int moveCount = size > 8 ? size - 8 : 0;
			writeByte((int) (data >>> moveCount), size > 8 ? 8 : size);
			size -= 8;
		}
	}
	/**
	 * writes 1 bit boolean to output stream
	 * @param data
	 */
	public void writeBoolean(boolean data){
		writeByte(data ? 1 : 0, 1);
	}
	/**
	 * writes byteArray to output stream
	 * @param data
	 */
	public void writeByteArray(byte[] data){
		writeByteArray(data, data.length);
	}
	/**
	 * writes size bytes to output stream from byte array
	 * @param data
	 * @param size
	 */
	public void writeByteArray(byte[] data, int size){
		for (int i = 0; i < size; i ++){
			writeByte(data[i]);
		}
	}	
}
