package ua.org.shaddy.anion.streamtools.codec;

import java.util.Arrays;

import junit.framework.TestCase;
import ua.org.shaddy.anion.streamtools.bitinputstream.ByteBitInputStream;
import ua.org.shaddy.anion.streamtools.bitoutputstream.ByteBitOutputStream;

public class BitStreamEncoderDecoderTest extends TestCase {
	private static final int TEST_COUNT = 255;
	private static final int INT_SIZE = 11;

	public void testEncodeDecodeLoop(){
		byte []input = new byte[TEST_COUNT];
		for (int i = 0; i < TEST_COUNT; i++){
			input[i] = (byte) i;
		}
		ByteBitInputStream bis = new ByteBitInputStream(input);
		BitStreamDecoder dec = new BitStreamDecoder(bis);
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamEncoder benc = new BitStreamEncoder(bos);
		for (int i = 0; i < TEST_COUNT / 2; i++){
			benc.writeBoolean(dec.loadBoolean());
			benc.writeBoolean(dec.loadBoolean());
			benc.writeBoolean(dec.loadBoolean());
			benc.writeBoolean(dec.loadBoolean());
			benc.writeBoolean(dec.loadBoolean());
			benc.writeBits(dec.loadBits(INT_SIZE), INT_SIZE);	
		}
		System.out.println(Arrays.toString(input));
		System.out.println(Arrays.toString(bos.getData()));
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
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
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
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
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
		ByteBitOutputStream bos = new ByteBitOutputStream(255);
		BitStreamEncoder benc = new BitStreamEncoder(bos);
		for (int i = 0; i < TEST_COUNT * 8; i++){
			benc.writeBoolean(dec.loadBoolean());
		}
		for (int i = 0; i < TEST_COUNT; i ++){
			assertEquals(input[i], (byte) bos.getData()[i]);
		}
	}
}
