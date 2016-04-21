package ua.org.shaddy.anion.streamtools.codec;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;
import ua.org.shaddy.anion.streamtools.codec.BitStreamDecoder;
import ua.org.shaddy.anion.tools.BitStreamException;

public class BitStreamDecoderTest extends TestCase {

	private static final String TEST_STRING= "test string";
	private static final int BITS_SIZE = 4096 * 16;
	public void testLoadBits() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals("a1b2c3d4", Long.toHexString(bsd.loadBits(32)));
		
		BitInputStream bis1 = new ByteBitInputStream(new byte[]{(byte) 0xa1, (byte) 0xb2, (byte) 0xc3, (byte) 0xd4});
		BitStreamDecoder bsd1 = new BitStreamDecoder(bis1);
		bsd1.setByteOrder(ByteOrder.BIG_ENDIAN);
		//
		//	loadBits must not consider a byte order
		//
		assertEquals("a1b2c3d4", Long.toHexString(bsd1.loadBits(32)));
	}
	
	public void testLoadBitsfirstSmall() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0, (byte) 0x1});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertFalse(bsd.loadBoolean());
		assertFalse(bsd.loadBoolean());
		assertEquals(1, bsd.loadBits(14));	
	}
	
	public void testLoadBitsfirstSmall1() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{1, 2});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertFalse(bsd.loadBoolean());
		assertFalse(bsd.loadBoolean());
		assertEquals(258, bsd.loadBits(14));	
	}
	
	public void testLoadBitsfirstSmall2() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 64, (byte) 65});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertFalse(bsd.loadBoolean());
		assertTrue(bsd.loadBoolean());
		assertEquals(65, bsd.loadBits(14));	
	}
	
	public void testLoadBitsLastSmall() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 0, (byte) 0x1});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(0, bsd.loadBits(14));
		assertFalse(bsd.loadBoolean());
		assertTrue(bsd.loadBoolean());
	}
	
	public void testLoadBoolean() {
		BitInputStream bis = new ByteBitInputStream(new byte[]{(byte) 170});
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		assertEquals(true, bsd.loadBoolean());
		assertEquals(false, bsd.loadBoolean());
		assertEquals(true, bsd.loadBoolean());
		assertEquals(false, bsd.loadBoolean());
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
		} catch (BitStreamException t){
		}
	}
	
	public void testReadBooleans() {
		boolean data[] = new boolean[BITS_SIZE * 8];
		for (int i = 0; i < BITS_SIZE; i++){
			for (int x = 0; x < 8; x++){
				data[ i * 8 + x ] = Math.random() > 0.5; 
			}
		}
		
		byte byteData[] = new byte[BITS_SIZE];
		for (int i = 0; i < BITS_SIZE; i++){
			byteData[i] = 0;
			int d = 0;
			for (int x = 0; x < 8; x ++){
				int val = data[i * 8 + x] ? 1 : 0;
				d = d | (val << (7 - x));
			}
			byteData[i] = (byte) d;
		}
		
		BitInputStream bis = new ByteBitInputStream(byteData);
		BitStreamDecoder bsd = new BitStreamDecoder(bis);
		for (int i = 0; i < BITS_SIZE * 8; i++){
			assertEquals(data[i], bsd.loadBoolean()); 
		}
		
	}
	
}
