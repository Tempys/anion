package ua.org.shaddy.anion.streamtools.bitinputstream;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;

public class TestBitInputStream extends TestCase{
	public void testByteLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {1, 2, 3, 4});
		assertEquals(1, bs.loadByte());
		assertEquals(2, bs.loadByte());
		assertEquals(3, bs.loadByte());
		assertEquals(4, bs.loadByte());
	}
	
	public void testCounter(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {1, 2, 3, 4});
		assertEquals(0, bs.getCounter());
		bs.loadByte();
		assertEquals(1, bs.getCounter());
		bs.loadByte();
		assertEquals(2, bs.getCounter());
		bs.resetCounter();
		assertEquals(0, bs.getCounter());
	}
}
