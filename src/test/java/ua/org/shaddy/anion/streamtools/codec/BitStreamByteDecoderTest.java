package ua.org.shaddy.anion.streamtools.codec;

import ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;
import ua.org.shaddy.anion.streamtools.codec.BitStreamByteDecoder;
import junit.framework.TestCase;

public class BitStreamByteDecoderTest extends TestCase{
	
	public void testByteLoading(){
		BitInputStreamInterface bs = new ByteBitInputStream(new byte[] {0x0a, 0x0b, 0x0c, 0x0d});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals(0x0a, bCodec.loadByte());
		assertEquals(0x0b, bCodec.loadByte());
		assertEquals(0x0c, bCodec.loadByte());
		assertEquals(0x0d, bCodec.loadByte());
		assertEquals(4, bs.getCounter());
	}
	
	public void testBitLoading(){
		BitInputStreamInterface bs = new ByteBitInputStream(new byte[] {(byte) 0xaa, 0x07, 0x0c, 0x0d});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(1, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(2, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(3, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(4, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(5, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(6, bCodec.getPadding());
		assertEquals(1, bCodec.loadByte(1));
		assertEquals(7, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(1));
		assertEquals(0, bCodec.getPadding());
		assertEquals(0, bCodec.loadByte(2));
		assertEquals(2, bCodec.getPadding());
	}
	
	public void testBitLoadingPaddings(){
		BitInputStreamInterface bs = new ByteBitInputStream(new byte[] {(byte) 0xfa, (byte) 0x0b});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals(Long.toHexString(0xf), Long.toHexString(bCodec.loadByte(4)));
		assertEquals(4, bCodec.getPadding());
		assertEquals(Long.toHexString(0xa0), Long.toHexString(bCodec.loadByte(8)));
		assertEquals(2, bs.getCounter());
	}
	
	public void testReadBytesNegative(){
		BitInputStreamInterface bs = new ByteBitInputStream(new byte[] {(byte) 128});
		BitStreamByteDecoder bCodec = new BitStreamByteDecoder(bs);
		assertEquals((byte) 128, (byte) bCodec.loadByte());
		assertEquals(1, bs.getCounter());
	}
	

	public void testReadBytesLoop(){
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++){
			bytes[i] = (byte) i;
		}
		ByteBitInputStream bis = new ByteBitInputStream(bytes);
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bis);
		for (int i = 0; i < 256; i++){
			assertEquals((byte) i, (byte) bse.loadByte());
		}
		assertEquals(256, bis.getCounter());
	}
	public void testReadBytesPartsLoop(){
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++){
			bytes[i] = (byte) i;
		}
		ByteBitInputStream bos = new ByteBitInputStream(bytes);
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bos);
		for (int i = 0; i < 256; i++){
			//System.out.println(i);
			assertEquals((byte) (i & 0xf0), (byte) (bse.loadByte(4) << 4));
			assertEquals((byte) (i & 0x0f), (byte) bse.loadByte(4));
			
		}
	}
	
	public void testReadBytesPartsLoop1(){
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++){
			bytes[i] = (byte) i;
		}
		ByteBitInputStream bos = new ByteBitInputStream(bytes);
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bos);
		for (int i = 0; i < 256; i++){
			assertEquals((byte) (i & 224), (byte) (bse.loadByte(3) << 5));
			assertEquals((byte) (i & 31), (byte) bse.loadByte(5));
		}
	}
	
	public void testReadBytesParts(){
		ByteBitInputStream bos = new ByteBitInputStream(new byte[] { 8, 32 });
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bos);
		assertEquals((byte) (8 & 224), (byte) (bse.loadByte(3) << 5));
		assertEquals((byte) (8 & 31), (byte) bse.loadByte(5));
		assertEquals((byte) (32 & 224), (byte) (bse.loadByte(3) << 5));
		assertEquals((byte) (32 & 31), (byte) bse.loadByte(5));
		
	}
	
	public void testReadBitsLoop(){
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++){
			bytes[i] = (byte) 0xff;
		}
		ByteBitInputStream bis = new ByteBitInputStream(bytes);
		BitStreamByteDecoder bse = new BitStreamByteDecoder(bis);
		for (int i = 0; i < 256 * 8; i++){
			assertEquals(1, (byte) bse.loadByte(1));
		}
		assertEquals(256, bis.getCounter());
	}
	
}
