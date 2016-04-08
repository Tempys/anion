package ua.org.shaddy.anion.streamtools.bitoutputstream;

import java.io.IOException;
import java.io.OutputStream;

import ua.org.shaddy.anion.tools.BitStreamException;

public class OsBitOutputStream extends BitOutputStream{
	
	private final OutputStream os;
	public OsBitOutputStream(OutputStream os) {
		this.os = os;
	}
	protected void writeByteNative(int data) {
		try {
			os.write(data);
		} catch (IOException e) {
			throw new BitStreamException("Error writing to stream", e);
		}
	}
}
