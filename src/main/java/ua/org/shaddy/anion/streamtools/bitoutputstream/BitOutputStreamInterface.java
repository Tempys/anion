package ua.org.shaddy.anion.streamtools.bitoutputstream;

public interface BitOutputStreamInterface {

	/**
	 * closes the stream
	 */
	void close();

	/**
	 * saves a byte to stream and increments a counter of saved bytes
	 * @return
	 */
	long writeByte(int data);

	/**
	 * returns a counter of saved bytes
	 * @return
	 */
	long getCounter();

	/**
	 * resets a counter of saved bytes
	 */
	void resetCounter();

}