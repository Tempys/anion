package ua.org.shaddy.anion.streamtools.bitoutputstream;
/**
 * so called stream to write data by 1 byte
 * @author shaddy
 *
 */
public abstract class BitOutputStream implements BitOutputStreamInterface {
	protected long counter = 0;
	/**
	 * saves single byte from bit stream and increments a counter
	 * @return
	 */
	protected abstract void writeByteNative(int data);
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStreaminterface#close()
	 */
	public abstract void close();
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStreaminterface#writeByte(int)
	 */
	public long writeByte(int data){
		writeByteNative(data);
		counter ++;
		return counter;
	}
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStreaminterface#getCounter()
	 */
	public long getCounter() {
		return counter;
	}
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitoutputstream.BitOutputStreaminterface#resetCounter()
	 */
	public void resetCounter(){
		counter = 0;
	}
}
