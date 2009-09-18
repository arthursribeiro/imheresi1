package com.googlecode.imheresi1.project;

/**
 * Class that handles the PersistenceManager class exceptions
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class PersistenceManagerException extends Exception {

	/**
	 * Constructor
	 * 
	 * @param reason
	 */
	public PersistenceManagerException(String reason) {
		super(reason);
	}

}
