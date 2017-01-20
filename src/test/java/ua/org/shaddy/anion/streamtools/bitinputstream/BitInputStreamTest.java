package ua.org.shaddy.anion.streamtools.bitinputstream;

import junit.framework.TestCase;

public class BitInputStreamTest extends TestCase {
	public void testByteLoading(){
		BitInputStreamInterface bs = new ByteBitInputStream(new byte[] {1, 2, 3, 4});
		assertEquals(1, bs.loadByte());
		assertEquals(2, bs.loadByte());
		assertEquals(3, bs.loadByte());
		assertEquals(4, bs.loadByte());
	}
	
	public void testCounter(){
		BitInputStreamInterface bs = new ByteBitInputStream(new byte[] {1, 2, 3, 4});
		assertEquals(0, bs.getCounter());
		bs.loadByte();
		assertEquals(1, bs.getCounter());
		bs.loadByte();
		assertEquals(2, bs.getCounter());
		bs.resetCounter();
		assertEquals(0, bs.getCounter());
	}
	
	public void testLoadByte() {
		BitInputStreamInterface bis = new BitInputStream(){
			int value = 0;
			protected int loadByteNative() {
				return value ++;
			}
			public void close() {
				
			}
		};
		assertEquals(0, bis.getCounter());
		assertEquals(0, bis.loadByte());
		assertEquals(1, bis.loadByte());
		assertEquals(2, bis.getCounter());
	}

	public void testResetCounter() {
		BitInputStreamInterface bis = new BitInputStream(){
			int value = 0;
			protected int loadByteNative() {
				return value ++;
			}
			public void close() {
				
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
		BitInputStreamInterface bis = new BitInputStream(){
			int value = 0;
			protected int loadByteNative() {
				return value ++;
			}
			public void close() {
				
			}
		};
		assertEquals(0, bis.getCounter());
		assertEquals(0, bis.loadByte());
		bis.skip(10);
		assertEquals(11, bis.loadByte());
		assertEquals(12, bis.getCounter());
	}

}
