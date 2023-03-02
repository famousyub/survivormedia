package org.zombie.apocalipse.api.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * envelopes the response (really??) so that everything has a pattern to follow
 * @author Itair Miguel
 *
 */
public class EnvelopeResponse {

	private Meta meta;
	private Object data;

	/**
	 * constructor that receives the data that has to be put in the envelope
	 * 
	 * @param data
	 *            - return data
	 */
	public EnvelopeResponse(Object data) {
		this.data = data;
	}

	/**
	 * constructor that receives meta data.
	 * 
	 * @param meta
	 *            {@link Meta}
	 */
	public EnvelopeResponse(Meta meta) {
		this.meta = meta;
	}

	/**
	 * meta-data class
	 */
	@JsonInclude(Include.NON_NULL)
	public static class Meta {

		private List<Error> errors;

		/**
		 * add an error to the meta-data
		 * 
		 * @param error
		 *            - known error
		 * @param message
		 *            - error message
		 */
		public void addError(String error, String message) {
			if (errors == null)
				errors = new ArrayList<>();

			errors.add(new Error(error, message));
		}

		public List<Error> getErrors() {
			return errors;
		}
	}

	/**
	 * class to detail the meta-data of an error
	 */
	@JsonInclude(Include.NON_NULL)
	static class Error {

		private String error;
		private String message;

		/**
		 * constructor that receives an error and a message
		 * 
		 * @param error
		 * @param message
		 */
		public Error(String error, String message) {
			this.error = error;
			this.message = message;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
