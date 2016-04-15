package ua.org.shaddy.anion.streamtools.codec;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.codec.BitStreamDecoder;
import ua.org.shaddy.anion.tools.BitStreamException;

public class BitStreamEncoderTest extends TestCase {

	private static final String TEST_STRING= "test string";
	
	private static final byte TEST_BYTE_1 = 0x55;
	private static final byte TEST_BYTE_2 = 0x0f;
	public void testIntLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {0x0a, 0x0b, 0x0c, 0x0d, TEST_BYTE_1, TEST_BYTE_2, TEST_BYTE_1, TEST_BYTE_2});
		BitStreamDecoder bsCodec = new BitStreamDecoder(bs);
		assertEquals(0x0a0b0c0d, bsCodec.loadInt32());
		assertEquals(TEST_BYTE_1 << 8 | TEST_BYTE_2, bsCodec.loadInt16());
		assertEquals(TEST_BYTE_1 << 8 | TEST_BYTE_2, bsCodec.loadLong(16));
	}
	
	public void testBigEndianIntLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {0x0a, 0x0b, 0x0c, 0x0d, TEST_BYTE_1, TEST_BYTE_2, TEST_BYTE_1, TEST_BYTE_2});
		BitStreamDecoder bsCodec = new BitStreamDecoder(bs);
		bsCodec.setByteOrder(ByteOrder.BIG_ENDIAN);
		assertEquals(0x0d0c0b0a, bsCodec.loadInt32());
		assertEquals(TEST_BYTE_2 << 8 | TEST_BYTE_1, bsCodec.loadInt16());
		assertEquals(TEST_BYTE_2 << 8 | TEST_BYTE_1, bsCodec.loadLong(16));
	}

	public void testLoadBits() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(0xd4c3b2a1L, bsd.loadBits(32));
		
		BitInputStream bis1 = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd1 = new BitStreamDecoder(bis1);
		bsd1.setByteOrder(ByteOrder.BIG_ENDIAN);
		//
		//	loadBits must not consider a byte order
		//
		assertEquals(0xd4c3b2a1L, bsd1.loadBits(32));
	}
	
	public void testLoadLong() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(0xA1b2c3d4L, bsd.loadLong(32));
		
		BitInputStream bis1 = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd1 = new BitStreamDecoder(bis1);
		bsd1.setByteOrder(ByteOrder.BIG_ENDIAN);
		assertEquals(0xd4c3b2A1L, bsd1.loadLong(32));
		
		
		BitInputStream bis2 = new ByteBitInputStream(new byte[]{
				(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4, 
				(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4
		});
		BitStreamDecoder bsd2 = new BitStreamDecoder(bis2);
		long val = bsd2.loadLong(64);
		assertEquals(0xA1b2c3d4A1b2c3d4L, val);
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
		assertEquals(0x1a2b3c4da1b2c3d4L, val);
	}
	
	public void testLoadLong64BigEndian() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{
				(byte) 0x1a, (byte) 0x2b, (byte) 0x3c, (byte) 0x4d,
				(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4
		});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		bsd.setByteOrder(ByteOrder.BIG_ENDIAN);
		long val = bsd.loadLong64();
		assertEquals(0xd4c3b2a14d3c2b1aL, val);
		
	}

	public void testLoadBoolean() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 170});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(false, bsd.loadBoolean());
		assertEquals(true, bsd.loadBoolean());
		assertEquals(false, bsd.loadBoolean());
		assertEquals(true, bsd.loadBoolean());
	}

	public void testLoadByteArray() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{
				(byte) 0x1a, (byte) 0x2b, (byte) 0x3c, (byte) 0x4d
		});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		byte[] ba = bsd.loadByteArray(4);
		assertEquals(0x1a, ba[0]);
		assertEquals(0x2b, ba[1]);
		assertEquals(0x3c, ba[2]);
		assertEquals(0x4d, ba[3]);
	}

	public void testLoadString() {
		BitInputStream bis = new ByteBitInputStream(TEST_STRING.getBytes());
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		String str = bsd.loadString(TEST_STRING.length());
		assertEquals(TEST_STRING, str);
	}
	
	public void testLoadStringEncoding() {
		BitInputStream bis = new ByteBitInputStream(TEST_STRING.getBytes());
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		String str = bsd.loadString(TEST_STRING.length(), "utf-8");
		assertEquals(TEST_STRING, str);
	}
	
	public void testLoadStringEncodingError() {
		BitInputStream bis = new ByteBitInputStream(TEST_STRING.getBytes());
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		try{
			String str = bsd.loadString(TEST_STRING.length(), "utf-88");
			fail("Exception is expected, but not thrown");
		} catch (Throwable t){
			assertTrue(t instanceof BitStreamException);
		}
	}
	
}
