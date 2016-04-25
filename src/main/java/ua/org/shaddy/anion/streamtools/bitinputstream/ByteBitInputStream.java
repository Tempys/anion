package ua.org.shaddy.anion.streamtools.bitinputstream;

import ua.org.shaddy.anion.tools.BitStreamEndsException;
import ua.org.shaddy.anion.tools.BitTools;

public class ByteBitInputStream extends BitInputStream {
	int pointer = 0; 
	private final byte[] data; 
	public ByteBitInputStream(byte[] data) {
		this.data = data;
	}
	
	protected int loadByteNative() {
		if (pointer > data.length - 1){
			throw new BitStreamEndsException();
		}
		return BitTools.byteToUnsigned(data[pointer++]);
	}
	
	public void skip(int skipSize) {
		counter += skipSize;
		pointer += skipSize;
	}
	
	public void close() {
		
	}
}
