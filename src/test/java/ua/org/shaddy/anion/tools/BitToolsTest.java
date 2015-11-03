package ua.org.shaddy.anion.tools;

import junit.framework.TestCase;

public class BitToolsTest extends TestCase {

	public void testByteToUnsigned() {
		assertEquals(1, BitTools.byteToUnsigned((byte) 1));
	}
	
	private byte checkConversion(byte number){
		return BitTools.unsignedToByte(BitTools.byteToUnsigned(number));
	}
	
	public void test() {
		assertEquals((byte) 0, checkConversion((byte) 0));
		assertEquals((byte) 1, checkConversion((byte) 1));
		assertEquals((byte) -1, checkConversion((byte) -1));
		assertEquals((byte) 127, checkConversion((byte) 127));
		assertEquals((byte) -127, checkConversion((byte) -127));
	}
	
	public void testIntToLong(){
		assertEquals(Integer.MIN_VALUE, (int) BitTools.intToLongUnsigned(Integer.MIN_VALUE));
		assertEquals(Integer.MAX_VALUE, (int) BitTools.intToLongUnsigned(Integer.MAX_VALUE));
		assertEquals(Integer.MIN_VALUE / 2, (int) BitTools.intToLongUnsigned(Integer.MIN_VALUE / 2));
		assertEquals(Integer.MAX_VALUE / 2, (int) BitTools.intToLongUnsigned(Integer.MAX_VALUE / 2));
		assertEquals(0, (int) BitTools.intToLongUnsigned(0));
	}
}
