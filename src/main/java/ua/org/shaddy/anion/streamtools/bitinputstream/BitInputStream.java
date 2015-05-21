package ua.org.shaddy.anion.streamtools.bitinputstream;

/**
 * so called stream to load data by 1 byte
 * @author shaddy
 *
 */
public abstract class BitInputStream {
	private long counter = 0;
	/**
	 * loads single byte from bit stream and increments a counter
	 * @return
	 */
	protected abstract int loadByteNative();
	/**
	 * loads a bytefrom stream and increments a counter of loaded bytes
	 * @return
	 */
	public int loadByte(){
		counter ++;
		return loadByteNative();
	}
	/**
	 * returns a counter of loaded bytes
	 * @return
	 */
	public long getCounter() {
		return counter;
	}
	/**
	 * resets a counter of loaded bytes
	 */
	public void resetCounter(){
		counter = 0;
	}
	/**
	 * skip some bytes in stream
	 * @param skipSize
	 */
	public void skip(int skipSize) {
		for (int i = 0; i < skipSize; i++){
			loadByte();
		}
	}
}