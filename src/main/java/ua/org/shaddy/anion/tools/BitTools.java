package ua.org.shaddy.anion.tools;

public class BitTools {
	
	public final static int[] bitMask = new int[] { 0, 1, 3, 7,	15, 31, 63, 127, 255};
	/**
	 * converts byte to unsigned byte (integer value)
	 * @param byteValue
	 * @return
	 */
	public static int byteToUnsigned(byte byteValue){
		return byteValue < 0 ? 256 + byteValue : byteValue;
	}
	
	public static byte unsignedToByte(int byteValue){
		return (byte) byteValue;
	}
}
