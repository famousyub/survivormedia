package org.zombie.apocalipse.api.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zombie.apocalipse.api.core.EnvelopeResponse;
import org.zombie.apocalipse.api.core.EnvelopeResponse.Meta;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.exception.ServiceException;

/***
 * controller that handles the exceptions returned
 * @author Itair Miguel
 *
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(GlobalControllerExceptionHandler.class);

	private Map<Class<? extends ServiceException>, HttpStatus> statusMap;
	
	/**
	 * controller with the HTTP Status for the exception
	 */
	public GlobalControllerExceptionHandler() {
		statusMap = new HashMap<Class<? extends ServiceException>, HttpStatus>();
		statusMap.put(ServiceException.class, HttpStatus.INTERNAL_SERVER_ERROR);
		statusMap.put(NotFoundException.class, HttpStatus.NOT_FOUND);
		statusMap.put(InvalidParamsException.class, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * handles unexpected exceptions
	 * @param exception {@link Exception} the exception
	 * @param request {@link HttpServletRequest} the request
	 * @return {@link EnvelopeResponse} an envelope with the error response
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public EnvelopeResponse exceptionHandler(Exception exception, HttpServletRequest request) {
		LOGGER.error("Wow, that was unexpected. Sorry for that.", exception);
		Meta meta = new EnvelopeResponse.Meta();
		meta.addError(exception.getClass().getSimpleName(), exception.getMessage());
		return new EnvelopeResponse(meta);
	}
	
	/**
	 * handles expected exceptions ({@link ServiceException}
	 * @param exception {@link ServiceException} the exception
	 * @param request {@link HttpServletRequest} the request
	 * @return {@link EnvelopeResponse} an envelope with the error response
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseEntity<EnvelopeResponse> exceptionHandler(ServiceException exception, HttpServletRequest request) {
		LOGGER.warn("Brace yourselves, Service alert!", exception);
		Meta meta = new EnvelopeResponse.Meta();
		meta.addError(exception.getClass().getSimpleName(), exception.getMessage());
		HttpStatus status = statusMap.get(exception.getClass());
		return ResponseEntity
			.status(status)
			.body(new EnvelopeResponse(meta));
	}
	
	/**
	 * handles bean errors
	 * @param ex {@link MethodArgumentNotValidException}
	 * @param request {@link HttpServletRequest} the request
	 * @return {@link EnvelopeResponse} an envelope with the error response
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public EnvelopeResponse exceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	private EnvelopeResponse processFieldErrors(List<FieldError> fieldErrors) {
		
		Meta meta = new EnvelopeResponse.Meta();
		fieldErrors.stream()
			.forEach(field -> 
				meta.addError("Invalid property " + field.getField(), field.getDefaultMessage()));
		
		return new EnvelopeResponse(meta);
	}
}
