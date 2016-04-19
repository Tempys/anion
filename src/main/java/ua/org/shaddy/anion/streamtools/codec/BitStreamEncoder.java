package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStream;

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
		while (size > 8) {
			writeByte((int)data);
			data = data >>> 8;
			size -= 8;
		}
		if (size > 0){
			writeByte((int)data, size);
		}
	}
	
	/**
	 * writes size(max 32) bits of data to bitOutputStream
	 * @param data
	 * @param size
	 */
	public void writeLong(long data, int size){
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			writeBits(data, size);
		} else {
			int startShift;
			startShift = (size >>> 3) << 3 ; 
			if (startShift == size){
				startShift -= 8;
			}
			
			while (startShift >= 0){
				writeByte((int)(data >>> startShift), size - startShift);
				startShift -= 8;
				size = startShift + 8;
			}
			
		}
	}
	/**
	 * writes int bits to output
	 * @param data
	 * @param size
	 */
	public void writeInt(int data, int size){
		writeLong(data, size);
	}
	
	public void writeInt32(int data) {
		writeLong(data, 32);
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
	
	public byte getByteOrder() {
		return byteOrder;
	}
	
	public void setByteOrder(byte byteOrder) {
		this.byteOrder = byteOrder;
	}
	
	
}
