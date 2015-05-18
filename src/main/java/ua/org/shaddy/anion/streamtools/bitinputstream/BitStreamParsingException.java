package ua.org.shaddy.anion.streamtools.bitinputstream;

public class BitStreamParsingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BitStreamParsingException() {
	}

	public BitStreamParsingException(String message) {
		super(message);
	}

	public BitStreamParsingException(Throwable cause) {
		super(cause);
	}

	public BitStreamParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BitStreamParsingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
