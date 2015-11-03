package ua.org.shaddy.anion.streamtools.bitinputstream;

import junit.framework.TestCase;

public class BitInputStreamTest extends TestCase {

	public void testLoadByte() {
		BitInputStream bis = new BitInputStream(){
			int value = 0;
			protected int loadByteNative() {
				return value ++;
			}
		};
		assertEquals(0, bis.getCounter());
		assertEquals(0, bis.loadByte());
		assertEquals(1, bis.loadByte());
		assertEquals(2, bis.getCounter());
	}

	public void testResetCounter() {
		BitInputStream bis = new BitInputStream(){
			int value = 0;
			protected int loadByteNative() {
				return value ++;
			}
		};
		assertEquals(0, bis.getCounter());
		assertEquals(0, bis.loadByte());
		bis.skip(10);
		assertEquals(11, bis.getCounter());
		bis.resetCounter();
		assertEquals(0, bis.getCounter());
	}

	public void testSkip() {
		BitInputStream bis = new BitInputStream(){
			int value = 0;
			protected int loadByteNative() {
				return value ++;
			}
		};
		assertEquals(0, bis.getCounter());
		assertEquals(0, bis.loadByte());
		bis.skip(10);
		assertEquals(11, bis.loadByte());
		assertEquals(12, bis.getCounter());
	}

}
