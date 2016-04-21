package ua.org.shaddy.anion.streamtools.codec;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;
import ua.org.shaddy.anion.tools.BitStreamException;

public class BitStreamByteEncoderTest extends TestCase {

	public void testSaveByte() {
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamByteEncoder enc = new BitStreamByteEncoder(bos);
		for (int i=0; i<255; i++){
			enc.writeByte(i);
			assertEquals((byte)i, bos.getData()[i]);	
		}
		assertEquals(255, bos.getCounter());
	}
	public void testSaveByteWithSize() {
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamByteEncoder enc = new BitStreamByteEncoder(bos);
		enc.writeByte(1, 1);
		enc.writeByte(0, 7);
		assertEquals(-128, bos.getData()[0]);
		enc.writeByte(1, 1);
		enc.writeByte(1, 7);
		assertEquals(-127, bos.getData()[1]);
		enc.writeByte(1, 1);
		enc.writeByte(1, 8);
		assertEquals(-128, bos.getData()[2]);
		enc.writeByte(1, 7);
		assertEquals(-127, bos.getData()[3]);
		assertEquals(4, bos.getCounter());
	}
	
	public void testPad(){
		ByteBitOutputStream bos = new ByteBitOutputStream(2);
		BitStreamByteEncoder enc = new BitStreamByteEncoder(bos);
		enc.writeByte((byte) 255);
		enc.writeByte((byte) 255, 4);
		enc.pad();
		assertEquals((byte) 255, bos.getData()[0]);
		assertEquals((byte) -16, bos.getData()[1]);
		assertEquals(2, bos.getCounter());
	}
	
	public void testSaveByteWithSizeMoreThan8() {
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamByteEncoder enc = new BitStreamByteEncoder(bos);
		try{
			enc.writeByte(1, 9);
			fail("writeByte with byte size of 9 bit must throw an exception");
		} catch (BitStreamException be){
			
		}	
	}
	
	public void testWriteBytesLoop(){
		ByteBitOutputStream bos = new ByteBitOutputStream(256);
		BitStreamByteEncoder bse = new BitStreamByteEncoder(bos);
		for (int i = 0; i < 256; i++){
			bse.writeByte((byte) i, 8);	
		}
		for (int i = 0; i < 256; i++){
			assertEquals((byte) i, (byte) bos.getData()[i]);
		}
		assertEquals(256, bos.getCounter());
	}
	
	public void testWriteBitsLoop(){
		ByteBitOutputStream bos = new ByteBitOutputStream(256);
		BitStreamByteEncoder bse = new BitStreamByteEncoder(bos);
		for (int i = 0; i < 256; i++){
			bse.writeByte((byte) ((i >> 4) & 0x0f), 4);
			bse.writeByte((byte) (i & 0x0f), 4);
		}
		for (int i = 0; i < 256; i++){
			assertEquals((byte) i, (byte) bos.getData()[i]);
		}
		assertEquals(256, bos.getCounter());
	}
	
}
