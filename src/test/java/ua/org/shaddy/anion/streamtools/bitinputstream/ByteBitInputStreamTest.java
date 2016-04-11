package ua.org.shaddy.anion.streamtools.bitinputstream;

import junit.framework.TestCase;
import ua.org.shaddy.anion.tools.BitStreamEndsException;

public class ByteBitInputStreamTest extends TestCase {

	public void testLoadByteNative() {
		ByteBitInputStream bs = new ByteBitInputStream(new byte[]{1, 2, 3, 4});
		assertEquals(1, bs.loadByte());
		assertEquals(2, bs.loadByte());
		assertEquals(3, bs.loadByte());
		assertEquals(4, bs.loadByte());
	}

	public void testSkip() {
		ByteBitInputStream bs = new ByteBitInputStream(new byte[]{1, 2, 3, 4});
		assertEquals(1, bs.loadByte());
		bs.skip(2);
		assertEquals(4, bs.loadByte());
		assertEquals(4, bs.getCounter());
	}
	
	public void testEnd(){
		ByteBitInputStream bs = new ByteBitInputStream(new byte[]{1, 2, 3, 4});
		int i = 0;
		try {
			for (; i<100; i++){
				bs.loadByte();
			}
			fail("Error, BitStreamEndsException exception must thrown");
		} catch (BitStreamEndsException e){
			assertEquals(i, 4);
		}
	}

}
