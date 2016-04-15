package ua.org.shaddy.anion.streamtools.codec;

import java.util.Arrays;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;

public class BitStreamEncoderDecoderTest extends TestCase {
	private static final int TEST_COUNT = 256;
	
	private static final int INT1_SIZE = 12;
	private static final int INT2_SIZE = 16 - INT1_SIZE;

	public void testEncodeDecodeLoop(){
		byte []input = new byte[TEST_COUNT];
		for (int i = 0; i < TEST_COUNT; i++){
			input[i] = (byte) i;
		}
		ByteBitInputStream bis = new ByteBitInputStream(input);
		BitStreamDecoder dec = new BitStreamDecoder(bis);
		ByteBitOutputStream bos = new ByteBitOutputStream(TEST_COUNT);
		BitStreamEncoder benc = new BitStreamEncoder(bos);
		for (int i = 0; i < TEST_COUNT / 2 ; i++){
			if (i == TEST_COUNT / 2){
				System.out.println("the last");
			}
			benc.writeBits(dec.loadBits(INT1_SIZE), INT1_SIZE);
			benc.writeBits(dec.loadBits(INT2_SIZE), INT2_SIZE);
		}
		for (int i = 0; i < TEST_COUNT; i ++){
			assertEquals((byte) input[i], (byte) bos.getData()[i]);
		}
	}
	
	public void testEncodeDecodeByteLoop(){
		byte []input = new byte[TEST_COUNT];
		for (int i = 0; i < TEST_COUNT; i++){
			input[i] = (byte) i;
		}
		ByteBitInputStream bis = new ByteBitInputStream(input);
		BitStreamDecoder dec = new BitStreamDecoder(bis);
		ByteBitOutputStream bos = new ByteBitOutputStream(TEST_COUNT);
		BitStreamEncoder benc = new BitStreamEncoder(bos);
		for (int i = 0; i < TEST_COUNT; i++){
			benc.writeByte(dec.loadByte());
		}
		for (int i = 0; i < TEST_COUNT; i ++){
			assertEquals(input[i], (byte) bos.getData()[i]);
		}
	}
	public void testEncodeDecode4ByteLoop(){
		byte []input = new byte[TEST_COUNT];
		for (int i = 0; i < TEST_COUNT; i++){
			input[i] = (byte) i;
		}
		ByteBitInputStream bis = new ByteBitInputStream(input);
		BitStreamDecoder dec = new BitStreamDecoder(bis);
		ByteBitOutputStream bos = new ByteBitOutputStream(TEST_COUNT);
		BitStreamEncoder benc = new BitStreamEncoder(bos);
		for (int i = 0; i < TEST_COUNT; i++){
			benc.writeByte(dec.loadByte(4), 4);
			benc.writeByte(dec.loadByte(4), 4);
		}
		for (int i = 0; i < TEST_COUNT; i ++){
			assertEquals(input[i], (byte) bos.getData()[i]);
		}
	}
	
	
	public void testEncodeDecodeBitLoop(){
		byte []input = new byte[TEST_COUNT];
		for (int i = 0; i < TEST_COUNT; i++){
			input[i] = (byte) i;
		}
		ByteBitInputStream bis = new ByteBitInputStream(input);
		BitStreamDecoder dec = new BitStreamDecoder(bis);
		ByteBitOutputStream bos = new ByteBitOutputStream(TEST_COUNT);
		BitStreamEncoder benc = new BitStreamEncoder(bos);
		for (int i = 0; i < TEST_COUNT * 8; i++){
			benc.writeBoolean(dec.loadBoolean());
		}
		for (int i = 0; i < TEST_COUNT; i ++){
			assertEquals(input[i], (byte) bos.getData()[i]);
		}
	}
}
