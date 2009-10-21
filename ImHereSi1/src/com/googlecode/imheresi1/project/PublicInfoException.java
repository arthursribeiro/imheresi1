package com.googlecode.imheresi1.project;

/**
 * Class that handles the PublicInfo class exceptions
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class PublicInfoException extends Exception {

	/**
	 * Constructor for the exception. 
	 * String passed is the error message
	 * @param motivo  - reason for which the exception was thrown.
	 */
	public PublicInfoException(String reason) {
		super(reason);
	}

}
