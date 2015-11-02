package ua.org.shaddy.anion.streamtools.bitoutputstream;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.codec.BitStreamByteEncoder;

public class BitStreamEncoder extends BitStreamByteEncoder{
	private byte byteOrder;
	/**
	 * By default byteOrder is LITTLE_ENDIAN
	 * @param bs
	 */
	public BitStreamEncoder(BitOutputStream bs) {
		this(bs, ByteOrder.LITTLE_ENDIAN);
	}
	public BitStreamEncoder(BitOutputStream bs, byte byteOrder) {
		super(bs);
		this.byteOrder = byteOrder;
	}
	/**
	 * writes size(max 32) bits of data to bitOutputStream
	 * @param data
	 * @param size
	 */
	public void writeInt(int data, int size){
		//
		// TODO: optimize this code
		//
		int res = 0;
		int startShift;
		if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
			//
			// INFO: code optimized for no divisions / multiplications
			//
			while (size > 8) {
				writeByte(data);
				data = data >>> 8;
				size -= 8;
			}
			if (size > 0){
				writeByte(data, size);
			}
		} else {
			//
			// TODO: optimize this
			//
			int times = size >>> 3;
			//
			//	fast multiplication by 8
			//
			startShift = (times << 3) - 8;
			while (startShift >= 8) {
				res = (data >>> startShift) & 255;
				writeByte(res);
				startShift -= 8;
				size -= 8;
			}
			if (size > 0){
				writeByte(data, size);
			}
		}
	}
	
	public void writeInt32(int data) {
		writeInt(data, 32);
	}
	
	public byte getByteOrder() {
		return byteOrder;
	}
	
	public void setByteOrder(byte byteOrder) {
		this.byteOrder = byteOrder;
	}
	
	
}
