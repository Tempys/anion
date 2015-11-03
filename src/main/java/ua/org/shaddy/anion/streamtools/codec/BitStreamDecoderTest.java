package ua.org.shaddy.anion.streamtools.codec;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;

public class BitStreamDecoderTest extends TestCase {

	public void testLoadInt() {
		
	}

	public void testLoadInt32() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(0xA1b2c3d4, bsd.loadInt32());
	}

	public void testLoadInt16() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(0xA1b2, bsd.loadInt16());
	}

	public void testLoadLong64() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{
				(byte) 0x1a, (byte) 0x2b, (byte) 0x3c, (byte) 0x4d,
				(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4
		});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		long val = bsd.loadLong64();
		System.out.println(Long.toHexString(val));
		assertEquals(0x1a2b3c4da1b2c3d4L, val);
		//assertEquals(0x4d3c2b1ad4c3b2a1L, bsd.loadLong64());
	}

	public void testLoadBoolean() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 170});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(false, bsd.loadBoolean());
		assertEquals(true, bsd.loadBoolean());
		assertEquals(false, bsd.loadBoolean());
		assertEquals(true, bsd.loadBoolean());
	}

	/*public void testLoadByteArray() {
		fail("Not yet implemented");
	}

	public void testLoadString() {
		fail("Not yet implemented");
	}*/

}
