package ua.org.shaddy.anion.streamtools.codec;

import java.util.Arrays;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;
import ua.org.shaddy.anion.streamtools.codec.BitStreamEncoder;
import ua.org.shaddy.anion.tools.BitTools;

public class BitStreamEncoderTest extends TestCase {

	public void testWriteRegularByte() {
		ByteBitOutputStream bos = new ByteBitOutputStream(5);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeByte(255);
		assertEquals((byte) 255, bos.getData()[0]);
	}
	public void testWriteBitsFirstSmall() {
		ByteBitOutputStream bos = new ByteBitOutputStream(5);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeBoolean(false);
		bse.writeBoolean(false);
		bse.writeBits(1, 14);
		assertEquals(0, bos.getData()[0]);
		assertEquals(1, bos.getData()[1]);
	}
	
	public void testWriteBitsFirstSmall1() {
		ByteBitOutputStream bos = new ByteBitOutputStream(6);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeBoolean(false);
		bse.writeBoolean(false);
		bse.writeBits(258, 14);
		assertEquals(1, bos.getData()[0]);
		assertEquals(2, bos.getData()[1]);
		bse.writeBoolean(false);
		bse.writeBoolean(false);
		bse.writeBits(0x2223, 14);
		assertEquals(34, bos.getData()[2]);
		assertEquals(35, bos.getData()[3]);
		bse.writeBoolean(false);
		bse.writeBoolean(true);
		bse.writeBits(0x4142, 14);
		assertEquals(65, bos.getData()[4]);
		assertEquals(66, bos.getData()[5]);
	}
	
	public void testWriteBitsLastSmall() {
		ByteBitOutputStream bos = new ByteBitOutputStream(5);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeBits(1, 14);
		bse.writeBoolean(false);
		bse.writeBoolean(false);
		assertEquals(0, bos.getData()[0]);
		assertEquals(4, bos.getData()[1]);
	}
	
	public void testWriteBitsSingle(){
		ByteBitOutputStream bos = new ByteBitOutputStream(8);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeBits(1, 12);
		bse.writeBits(0, 4);
		assertEquals(16, bos.getData()[1]);
	}
	
	public void testWriteBits(){
		ByteBitOutputStream bos = new ByteBitOutputStream(8);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeBits(0xa0L << 56 | 0xb0L << 48| 0xc0L << 40 | 0xd0L << 32 | 0x01L << 24 | 0x02L << 16 | 0x03L << 8 | 0x04L, 64);
		assertEquals((byte) 0xa0, bos.getData()[0]);
		assertEquals((byte) 0xb0, bos.getData()[1]);
		assertEquals((byte) 0xc0, bos.getData()[2]);
		assertEquals((byte) 0xd0, bos.getData()[3]);
		assertEquals((byte) 0x01, bos.getData()[4]);
		assertEquals((byte) 0x02, bos.getData()[5]);
		assertEquals((byte) 0x03, bos.getData()[6]);
		assertEquals((byte) 0x04, bos.getData()[7]);
	}
	
	public void testWriteDifferentData(){
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeBoolean(true);
		bse.writeBoolean(false);
		bse.writeBits(0, 14);
		assertEquals(-128, bos.getData()[0]);
	}
	
	/*public void testWriteBitsLoop(){
		ByteBitOutputStream bos = new ByteBitOutputStream(256);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		for (int i = 0; i < 128; i++){
			bse.writeBits(i, 3);
			bse.writeBits((i >>> 3) & 8191, 13);
		}
		byte[] data = bos.getData();
		for (int i = 0; i < 128; i++){
			int pointer = i * 2;
			assertEquals(i & 7, (data[pointer] & BitTools.invertedBackBitMask[3]) >>> 5);
			assertEquals(i & 8191, ((data[pointer] & BitTools.bitMask[5]) << 8) | data[pointer + 1]);
		}
		System.out.println(Arrays.toString(bos.getData()));
	}*/
	
	
}
