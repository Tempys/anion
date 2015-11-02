package ua.org.shaddy.anion.streamtools.bitoutputstream;

import ua.org.shaddy.anion.tools.BitTools;

public class ByteBitOutputStream extends BitOutputStream{

	private byte[] data;
	private int pointer = 0;

	public ByteBitOutputStream(byte[] data){
		this.data = data;
	}
	
	public ByteBitOutputStream(int size){
		this.data = new byte[size];
	}
	
	protected void writeByteNative(int dataByte) {
		data[pointer] = BitTools.unsignedToByte(dataByte);
		pointer++;
	}

	public byte[] getData() {
		return data;
	}
}
