package ua.org.shaddy.anion.tools;

public class BitTools {
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
