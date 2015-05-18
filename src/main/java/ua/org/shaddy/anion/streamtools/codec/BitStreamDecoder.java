package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;

public class BitStreamDecoder extends BitStreamByteDecoder {

	private byte byteOrder = ByteOrder.LITTLE_ENDIAN;

	public BitStreamDecoder(BitInputStream bs) {
		super(bs);
	}

	/**
	 * loads {@value size} bytes to int
	 * 
	 * @param size
	 * @return
	 */
	public int loadInt(int size) {
		//
		// TODO: optimize this code
		//
		int res = 0;
		int startShift;
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			//
			// INFO: code optimized for no divisions / multiplications
			//
			startShift = 0;
			while (size > 0) {
				res = res | loadByte(size) << startShift;
				size -= 8;
				startShift += 8;
			}
		} else {
			//
			// TODO: optimize this
			//
			int times = size / 8 - 1;
			startShift = times * 8;
			while (startShift >= 8) {
				res = res | loadByte(8) << startShift;
				startShift -= 8;
				size -= 8;
			}
			if (size > 0){
				res = res | loadByte(size);
			}
		}
		return res;
	}

	/**
	 * loads 32 bit from {@link BitInputStream}
	 * 
	 * @return
	 */
	public int loadInt32() {
		int res;
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			res = loadByte() | loadByte() << 8 | loadByte() << 16
					| loadByte() << 24;
		} else {
			res = loadByte() << 24 | loadByte() << 16 | loadByte() << 8
					| loadByte();
		}
		return res;
	}

	/**
	 * loads 16 bit from {@link BitInputStream}
	 * 
	 * @return
	 */
	public int loadInt16() {
		int res;
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			res = loadByte() | loadByte() << 8;
		} else {
			res = loadByte() << 8 | loadByte();
		}
		return res;
	}

	/**
	 * loads 16 bit from {@link BitInputStream}
	 * 
	 * @return
	 */
	public long loadLong64() {
		long res;
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			res = loadByte() | loadByte() << 8 | loadByte() << 16
					| loadByte() << 24 | loadByte() << 32 | loadByte() << 48;
		} else {
			res = loadByte() | loadByte() << 8 | loadByte() << 16
					| loadByte() << 24 | loadByte() << 32 | loadByte() << 48;
		}
		return res;
	}

	/**
	 * returns byte order {@link ByteOrder}
	 * 
	 * @return
	 */
	public byte getByteOrder() {
		return byteOrder;
	}

	/**
	 * sets byte order {@link ByteOrder}
	 * 
	 * @param byteOrder
	 */
	public void setByteOrder(byte byteOrder) {
		this.byteOrder = byteOrder;
	}
	/**
	 * loads an 1 bit boolean from stream
	 * @return
	 */
	public boolean loadBoolean() {
		//
		//	TODO:optimize this
		//
		return loadByte(1) != 0;
	}
	/**
	 * loads a byte array with given size
	 * @param size
	 * @return
	 */
	public byte[] loadByteArray(int size) {
		byte[] byteArray = new byte[size];
		for (int i = 0; i < size; i++){
			byteArray[i] = (byte) loadByte();
		}
		return byteArray;
	}
	/**
	 * loads a string from stream
	 * @param size
	 * @return
	 */
	public String loadString(int size){
		return new String(loadByteArray(size));
	}
}
