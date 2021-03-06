package ua.org.shaddy.anion.streamtools.codec;

import java.io.UnsupportedEncodingException;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface;
import ua.org.shaddy.anion.tools.BitStreamException;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamDecoder extends BitStreamByteDecoder {

	private byte byteOrder = ByteOrder.BIG_ENDIAN;

	public BitStreamDecoder(BitInputStreamInterface bs) {
		super(bs);
	}
	
	public BitStreamDecoder(BitInputStreamInterface bs, byte byteOrder) {
		super(bs);
		this.byteOrder = byteOrder;
	}
	
	/**
	 * function reads a number of bits from a stream  
	 * @param size
	 * @return
	 */
	public long loadBits(int size) {
		long res = 0;
		int moveCount;
		int loadCount;
		while (size > 0) {
			if (size > 8){
				moveCount = size - 8;
				loadCount = 8;
			} else {
				moveCount = 0;
				loadCount = size;
			}
			res = res | ((long) loadByte(loadCount) << moveCount);
			size -= 8;
		}
		return res;
	}
	
	/**
	 * loads 16 bits into int
	 * @return
	 */
	public int loadShort(){
		return (int) loadBits(16);
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
