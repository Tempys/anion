package ua.org.shaddy.anion.streamtools.bitinputstream;

public interface BitInputStreamInterface {

	void close();

	/**
	 * loads a bytefrom stream and increments a counter of loaded bytes
	 * @return
	 */
	int loadByte();

	/**
	 * returns a counter of loaded bytes
	 * @return
	 */
	long getCounter();

	/**
	 * resets a counter of loaded bytes
	 */
	void resetCounter();

	/**
	 * skip some bytes in stream
	 * @param skipSize
	 */
	void skip(int skipSize);

}