package ua.org.shaddy.anion.streamtools.codec;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;

public class BitStreamByteEncoderTest extends TestCase {

	public void testSaveByte() {
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamByteEncoder enc = new BitStreamByteEncoder(bos);
		for (int i=0; i<255; i++){
			enc.saveByte(i);
			assertEquals((byte)i, bos.getData()[i]);	
		}
	}
	public void testSaveByteWithSize() {
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamByteEncoder enc = new BitStreamByteEncoder(bos);
		enc.saveByte(1, 1);
		enc.saveByte(0, 7);
		assertEquals(1, bos.getData()[0]);
		enc.saveByte(1, 1);
		enc.saveByte(1, 7);
		assertEquals(3, bos.getData()[1]);
		enc.saveByte(1, 1);
		enc.saveByte(1, 8);
		assertEquals(3, bos.getData()[2]);
		enc.saveByte(1, 7);
		assertEquals(2, bos.getData()[3]);
	}

}
