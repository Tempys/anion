package ua.org.shaddy.anion.streamtools.codec;

import java.io.UnsupportedEncodingException;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.tools.BitStreamException;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamDecoder extends BitStreamByteDecoder {

	private byte byteOrder = ByteOrder.LITTLE_ENDIAN;

	public BitStreamDecoder(BitInputStream bs) {
		super(bs);
	}
	/**
	 * function reads a number of bits from a stream, the main difference from {@link BitStreamDecoder#loadLong(int)} is that this function loading bits always in LITTLE_ENDIAN  
	 * @param size
	 * @return
	 */
	public long loadBits(int size) {
		long res = 0;
		byte startShift;
		startShift = 0;
		while (size > 0) {
			res = res | (long) loadByte(size > 8 ? 8 : size) << startShift;
			size -= 8;
			startShift += 8;
		}
		return res;
		
		
	}
	/**
	 * loads {@value size} bytes to int
	 * 
	 * @param size
	 * @return
	 */
	public long loadLong(int size) {
		//
		// TODO: optimize this code
		//
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			return loadBits(size);
		} else {
			
			long res = 0;
			int startShift;
			int times = (size >> 3) - 1;
			//
			//	fast multiplication by 8
			//
			startShift = times << 3;
			while (startShift >= 8) {
				res = res | ((long)loadByte(8) << startShift);
				startShift -= 8;
				size -= 8;
			}
			if (size > 0){
				res = res | loadByte(size > 8 ? 8 : size );
			}
			return res;
		}
	}
	
	/**
	 * loads size bytes to int
	 * @param size
	 * @return
	 */
	public int loadInt(int size) {
		return (int) loadLong(size);
	}
	

	/**
	 * loads 32 bit from {@link BitInputStream}
	 * 
	 * @return
	 */
	public int loadInt32() {
		int r1 = loadInt16();
		int r2 = loadInt16();
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			return (r2  << 16) | r1 ;
		} else {
			return (r1  << 16) | r2; 
			
		}
	}

	/**
	 * loads 16 bit from {@link BitInputStream}
	 * 
	 * @return
	 */
	public int loadInt16() {
		int res;
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			res = loadByte() | (loadByte() << 8);
		} else {
			res = (loadByte() << 8) | loadByte();
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
		int r1 = loadInt32();
		int r2 = loadInt32();
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			res = BitTools.intToLongUnsigned(r2) << 32 | BitTools.intToLongUnsigned(r1);
		} else {
			res = BitTools.intToLongUnsigned(r1) << 32 | BitTools.intToLongUnsigned(r2);
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
		loadByteArray(byteArray);
		return byteArray;
	}
	/**
	 * fills byteArrray from input stream
	 * @param byteArray
	 */
	public void loadByteArray(byte[] byteArray) {
		loadByteArray(byteArray, byteArray.length);
	}
	/**
	 * fills size bytes of byteArrray from input stream
	 * @param byteArray
	 * @param size
	 */
	public void loadByteArray(byte[] byteArray, int size) {
		for (int i = 0; i < size; i++){
			byteArray[i] = (byte) loadByte();
		}
	}
	
	
	/**
	 * loads a string from stream
	 * @param size
	 * @return
	 */
	public String loadString(int size){
		return new String(loadByteArray(size));
	}
	/**
	 * loads a string from stream
	 * @param size
	 * @param charsetName
	 * @return
	 */
	public String loadString(int size, String charsetName){
		try {
			return new String(loadByteArray(size), charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new BitStreamException("Error decoding string", e);
		}
	}
}
