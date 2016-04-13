package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStream;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;
import ua.org.shaddy.anion.streamtools.codec.BitStreamByteDecoder;
import junit.framework.TestCase;

public class BitStreamByteDecoderTest extends TestCase{
	
	public void testByteLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {0x0a, 0x0b, 0x0c, 0x0d});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals(0x0a, bCodec.loadByte());
		assertEquals(0x0b, bCodec.loadByte());
		assertEquals(0x0c, bCodec.loadByte());
		assertEquals(0x0d, bCodec.loadByte());
	}
	
	public void testBitLoading(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {(byte) 0xaa, 0x07, 0x0c, 0x0d});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(1, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(2, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(3, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(4, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(5, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(6, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(7, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(0, bCodec.getPadding());
		assertEquals(3, bCodec.loadByte(2));
		assertEquals(2, bCodec.getPadding());
	}
	
	public void testBitLoadingPaddings(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {(byte) 0xcc, 0x07, 0x0c, 0x0d});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals(0x0c, bCodec.loadByte(4));
		assertEquals(4, bCodec.getPadding());
		assertEquals(0x67, bCodec.loadByte(7));
	}
	
	public void testReadBytesNegative(){
		BitInputStream bs = new ByteBitInputStream(new byte[] {(byte) 128});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals((byte) 128, (byte) bCodec.loadByte());
	}
	

	public void testReadBytesLoop(){
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++){
			bytes[i] = (byte) i;
		}
		ByteBitInputStream bos = new ByteBitInputStream(bytes);
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bos);
		for (int i = 0; i < 256; i++){
			assertEquals((byte) i, (byte) bse.loadByte());
		}
	}
	
	public void testReadBitsLoop(){
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++){
			bytes[i] = (byte) 0xff;
		}
		ByteBitInputStream bos = new ByteBitInputStream(bytes);
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bos);
		for (int i = 0; i < 256 * 8; i++){
			assertEquals(1, (byte) bse.loadByte(1));
		}
	}
	
}
