package de.drippinger.exception;

/**
 * The Class CrawlerException.
 *
 * @author Dennis Rippinger
 */
public class CrawlerException extends Exception {

	private static final long serialVersionUID = 8307212821761023653L;

	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param message the message
	 */
	public CrawlerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public CrawlerException(String message, Throwable cause) {
		super(message, cause);
	}

}