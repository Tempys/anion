package ua.org.shaddy.anion.streamtools.bitinputstream;

import ua.org.shaddy.anion.tools.BitStreamEndsException;

/**
 * so called stream to load data by 1 byte
 * @author shaddy
 *
 */
public abstract class BitInputStream implements BitInputStreamInterface {
	protected long counter = 0;
	/**
	 * loads single byte from bit stream and increments a counter, throws {@link BitStreamEndsException} when stream ends;
	 * @return
	 */
	protected abstract int loadByteNative();
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface#close()
	 */
	public abstract void close();
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface#loadByte()
	 */
	public int loadByte(){
		int result = loadByteNative();
		counter ++;
		return result;
	}
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface#getCounter()
	 */
	public long getCounter() {
		return counter;
	}
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface#resetCounter()
	 */
	public void resetCounter(){
		counter = 0;
	}
	/* (non-Javadoc)
	 * @see ua.org.shaddy.anion.streamtools.bitinputstream.BitInputStreamInterface#skip(int)
	 */
	public void skip(int skipSize) {
		for (int i = 0; i < skipSize; i++){
			loadByte();
		}
	}
}
