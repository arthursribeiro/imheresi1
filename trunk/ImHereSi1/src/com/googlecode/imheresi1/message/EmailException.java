package com.googlecode.imheresi1.message;

/**
 * Class that handles the Email class exceptions
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class EmailException extends Exception {

	/**
	 * Constructor
	 * 
	 * @param reason
	 */
	public EmailException(String motivo) {
		super(motivo);
	}

}
