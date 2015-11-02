package ua.org.shaddy.anion.streamtools.bitoutputstream;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;

public class BitOutputStreamTest extends TestCase {

	public void testSaveByteNative() {
		ByteBitOutputStream bos = new ByteBitOutputStream(3);
		bos.writeByte(25);
		assertEquals(25, bos.getData()[0]);
		
		bos.writeByte(127);
		assertEquals(127, bos.getData()[1]);
		bos.writeByte(244);
		assertEquals((byte) -12, bos.getData()[2]);
	}
	
	public void testCounter(){
		ByteBitOutputStream bos = new ByteBitOutputStream(3);
		assertEquals(0, bos.getCounter());
		bos.writeByte(1);
		assertEquals(1, bos.getCounter());
		bos.writeByte(123);
		assertEquals(2, bos.getCounter());
		bos.writeByte(123);
		assertEquals(3, bos.getCounter());
	}
	public void testIndexOutOfBounds(){
		try{
			ByteBitOutputStream bos = new ByteBitOutputStream(3);
			bos.writeByte(1);
			bos.writeByte(1);
			bos.writeByte(1);
			bos.writeByte(1);
			fail("Expected exception");
		} catch (ArrayIndexOutOfBoundsException e){
			 assertEquals( "3", e.getMessage() );
		}
	}
}
