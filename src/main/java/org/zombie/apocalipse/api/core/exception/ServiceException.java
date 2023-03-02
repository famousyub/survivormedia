package org.zombie.apocalipse.api.core.exception;

/**
 * represents the general exception
 * @author Itair Miguel
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -7865319034708575658L;
	private static final String DEFAULT_MESSAGE = "Sorry pal. We were not able to process the request :("; 

	/**
	 * constructor to inform a customized message and the previous exception
	 * @param message The exception message
	 * @param cause The causing exception (previous)
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor to inform a customized message
	 * @param message The message of the exception
	 */
	public ServiceException(String message) {
		super(message);
	}
	
	/**
	 * constructor of the default message passing the cause
	 * @param cause cause of the exception
	 */
	public ServiceException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}
}
