package org.zombie.apocalipse.api.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe de resposta expecífica para mensagens.
 * 
 * @author Otávio Calaça Xavier
 *
 */
@JsonInclude(Include.NON_NULL)
public class MessageResponse extends EnvelopeResponse {
	
	/**
	 * Meta class specific to inform a message
	 */
	@JsonInclude(Include.NON_NULL)
	public static class MessageMeta extends Meta {
		private String message;
		
		/**
		 * constructor of the message
		 * @param message string representing tje message
		 */
		public MessageMeta(String message) {
			super();
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}

	/**
	 *constructor off the MessageResponse class
	 * @param message string representing the message
	 */
	public MessageResponse(String message) {
		super(new MessageMeta(message));
	}

	/**
	 * creates an object of the class passing the message
	 * @param message string representing the message
	 * @return {@link MessageResponse} message content
	 */
	public static MessageResponse build(String message) {
		return new MessageResponse(message);
	}
}
