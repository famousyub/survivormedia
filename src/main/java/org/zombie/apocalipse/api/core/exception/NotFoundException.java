package org.zombie.apocalipse.api.core.exception;

/**
 * if this guy is thrown there are no cookies for you this time.
 * @author Itair Miguel
 *
 */
public class NotFoundException extends ServiceException {

	private static final long serialVersionUID = -8308104984419907039L;
	
	private static final String DEFAULT_MESSAGE = "We tried, but there was nothing here..."; 

	/**
	 * constructor to inform a customized message and the previous exception
	 * @param message The exception message
	 * @param cause The causing exception (previous)
	 */
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor to inform a customized message
	 * @param message The message of the exception
	 */
	public NotFoundException(String message) {
		super(message);
	}
	
	/**
	 * constructor of the default message passing the cause
	 * @param cause cause of the exception
	 */
	public NotFoundException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}
}
