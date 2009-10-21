package com.googlecode.imheresi1.message;

/**
 * Class that handles the MessageController class exceptions
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class MessageControllerException extends Exception {

	/**
	 * Constructor for the exception. 
	 * String passed is the error message
	 * @param motivo - reason for which the exception was thrown.
	 */
	public MessageControllerException(String reason) {
		super(reason);
	}

}
