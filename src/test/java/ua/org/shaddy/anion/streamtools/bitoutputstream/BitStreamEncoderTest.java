package ua.org.shaddy.anion.streamtools.bitoutputstream;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.ByteOrder;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;

public class BitStreamEncoderTest extends TestCase {

	public void testWriteRegularByte() {
		ByteBitOutputStream bos = new ByteBitOutputStream(5);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeByte(255);
		assertEquals((byte) 255, bos.getData()[0]);
	}
	
	public void testWriteInt() {
		ByteBitOutputStream bos = new ByteBitOutputStream(6);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeInt(65535, 12);
		bse.pad();
		assertEquals((byte) 255, bos.getData()[0]);
		assertEquals((byte) 15, bos.getData()[1]);
	}
	
	public void testWriteInt32(){
		ByteBitOutputStream bos = new ByteBitOutputStream(6);
		BitStreamEncoder bse = new BitStreamEncoder(bos);
		bse.writeInt32(0x01 << 24 | 0x02 << 16 | 0x03 << 8 | 0x04);
		assertEquals((byte) 0x04, bos.getData()[0]);
		assertEquals((byte) 0x03, bos.getData()[1]);
		assertEquals((byte) 0x02, bos.getData()[2]);
		assertEquals((byte) 0x01, bos.getData()[3]);
	}
	
	public void testWriteInt32BigEndian(){
		ByteBitOutputStream bos = new ByteBitOutputStream(6);
		BitStreamEncoder bse = new BitStreamEncoder(bos, ByteOrder.BIG_ENDIAN);
		bse.writeInt32(0x01 << 24 | 0x02 << 16 | 0x03 << 8 | 0x04);
		assertEquals((byte) 0x01, bos.getData()[0]);
		assertEquals((byte) 0x02, bos.getData()[1]);
		assertEquals((byte) 0x03, bos.getData()[2]);
		assertEquals((byte) 0x04, bos.getData()[3]);
	}
		
	public void testWriteLittleEndian(){
		ByteBitOutputStream bos = new ByteBitOutputStream(6);
		BitStreamEncoder bse = new BitStreamEncoder(bos, ByteOrder.BIG_ENDIAN);
		bse.writeInt(0x01 << 8 | 0x02, 12);
		assertEquals((byte) 33, bos.getData()[0]);
		assertEquals((byte) 0, bos.getData()[1]);
	}
}
