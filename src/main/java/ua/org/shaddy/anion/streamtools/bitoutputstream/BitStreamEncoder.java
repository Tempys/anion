package ua.org.shaddy.anion.streamtools.bitoutputstream;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.codec.BitStreamByteEncoder;
import ua.org.shaddy.anion.tools.BitTools;

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
			//	fast mod 8
			//
			startShift = (size >>> 3) << 3 ; 
			if (startShift == size){
				startShift -= 8;
			}
			
			while (startShift >= 0){
				writeByte(data >>> startShift, size - startShift);
				startShift -= 8;
				size = startShift + 8;
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
