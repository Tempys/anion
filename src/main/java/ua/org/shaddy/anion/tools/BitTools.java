package ua.org.shaddy.anion.tools;

public class BitTools {
	
	public final static int[] bitMask = new int[] { 0, 1, 3, 7,	15, 31, 63, 127, 255};
	public final static int[] invertedBitMask = new int[] { 0 ^ 255, 1 ^ 255, 3 ^ 255, 7 ^ 255,	15 ^ 255, 31 ^ 255, 63 ^ 255, 127 ^ 255, 255 ^ 255};
	/**
	 * converts byte to unsigned byte (integer value)
	 * @param byteValue
	 * @return
	 */
	public static int byteToUnsigned(byte byteValue){
		//return byteValue < 0 ? 256 + byteValue : byteValue;
		return byteValue & 255;
	}
	
	public static byte unsignedToByte(int byteValue){
		return (byte) byteValue;
	}
	public static long intToLongUnsigned(int value){
		return ((long) value) & 0xFFFFFFFFL;
	}
}
