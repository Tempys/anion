package ua.org.shaddy.anion.tools;

public class BitStreamEndsException extends BitStreamException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5405507933792396869L;

	public BitStreamEndsException() {
	}

	public BitStreamEndsException(String message) {
		super(message);
	}

	public BitStreamEndsException(Throwable cause) {
		super(cause);
	}

	public BitStreamEndsException(String message, Throwable cause) {
		super(message, cause);
	}

	public BitStreamEndsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
