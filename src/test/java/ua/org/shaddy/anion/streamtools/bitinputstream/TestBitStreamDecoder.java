package ua.org.shaddy.anion.streamtools.bitinputstream;

import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.codec.BitStreamDecoder;
import junit.framework.TestCase;

public class TestBitStreamDecoder extends TestCase{
	private static final byte TEST_BYTE_1 = 0x55;
	private static final byte TEST_BYTE_2 = 0x0f;
	public void testIntLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {0x0a, 0x0b, 0x0c, 0x0d, TEST_BYTE_1, TEST_BYTE_2, TEST_BYTE_1, TEST_BYTE_2});
		BitStreamDecoder bsCodec = new BitStreamDecoder(bs);
		assertEquals(0x0a0b0c0d, bsCodec.loadInt32());
		assertEquals(TEST_BYTE_1 << 8 | TEST_BYTE_2, bsCodec.loadInt16());
		assertEquals(TEST_BYTE_1 << 8 | TEST_BYTE_2, bsCodec.loadInt(16));
	}
	
	public void testBigEndianIntLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {0x0a, 0x0b, 0x0c, 0x0d, TEST_BYTE_1, TEST_BYTE_2, TEST_BYTE_1, TEST_BYTE_2});
		BitStreamDecoder bsCodec = new BitStreamDecoder(bs);
		bsCodec.setByteOrder(ByteOrder.BIG_ENDIAN);
		assertEquals(0x0d0c0b0a, bsCodec.loadInt32());
		assertEquals(TEST_BYTE_2 << 8 | TEST_BYTE_1, bsCodec.loadInt16());
		assertEquals(TEST_BYTE_2 << 8 | TEST_BYTE_1, bsCodec.loadInt(16));
	}
}
