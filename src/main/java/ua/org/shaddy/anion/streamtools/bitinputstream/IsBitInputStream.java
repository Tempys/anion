package ua.org.shaddy.anion.streamtools.bitinputstream;

import java.io.IOException;
import java.io.InputStream;

import ua.org.shaddy.anion.tools.BitStreamEndsException;
import ua.org.shaddy.anion.tools.BitStreamException;

public class IsBitInputStream extends BitInputStream {
	int pointer = 0; 
	private final InputStream data; 
	public IsBitInputStream(InputStream data) {
		this.data = data;
	}
	
	protected int loadByteNative() {
		try {
			int res = data.read();
			if (res == -1){
				throw new BitStreamEndsException();
			}
			return res;
		} catch (IOException e) {
		throw new BitStreamException("Error reading input stream", e);
		}
	}

}
