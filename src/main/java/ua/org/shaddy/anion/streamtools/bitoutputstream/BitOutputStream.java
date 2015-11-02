package ua.org.shaddy.anion.streamtools.bitoutputstream;
/**
 * so called stream to write data by 1 byte
 * @author shaddy
 *
 */
public abstract class BitOutputStream {
	protected long counter = 0;
	/**
	 * saves single byte from bit stream and increments a counter
	 * @return
	 */
	protected abstract void writeByteNative(int data);
	/**
	 * saves a byte to stream and increments a counter of saved bytes
	 * @return
	 */
	public long writeByte(int data){
		writeByteNative(data);
		counter ++;
		return counter;
	}
	/**
	 * returns a counter of saved bytes
	 * @return
	 */
	public long getCounter() {
		return counter;
	}
	/**
	 * resets a counter of saved bytes
	 */
	public void resetCounter(){
		counter = 0;
	}
}
