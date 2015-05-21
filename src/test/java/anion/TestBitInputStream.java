package anion;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import junit.framework.TestCase;

public class TestBitInputStream extends TestCase{
	public void testByteLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {1, 2, 3, 4});
		assertEquals(1, bs.loadByte());
		assertEquals(2, bs.loadByte());
		assertEquals(3, bs.loadByte());
		assertEquals(4, bs.loadByte());
	}
}