package ua.org.shaddy.anion.streamtools.bitinputstream;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.codec.BitStreamDecoder;
import ua.org.shaddy.anion.tools.BitStreamException;

public class BitStreamDecoderTest extends TestCase {

	private static final String TEST_STRING= "test string";

	public void testLoadBits() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(0xA1b2c3d4L, bsd.loadBits(32));
		
		BitInputStream bis1 = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd1 = new BitStreamDecoder(bis1);
		bsd1.setByteOrder(ByteOrder.BIG_ENDIAN);
		//
		//	loadBits must not consider a byte order
		//
		assertEquals(0xA1b2c3d4L, bsd1.loadBits(32));
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
		System.out.println(Long.toHexString(val));
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
