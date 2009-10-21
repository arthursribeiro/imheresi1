package com.googlecode.imheresi1.message;

/**
 * Message Interface
 * This interface dictates that every message needs to declare it's path and a how to be build
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public interface Message {

	/**
	 * Method to return the path and archive name to save the message in.
	 * @return string representing the messages path.
	 */
	public String getPath();

	/**
	 * Method to return the whole message, already formated and ready to be sent  
	 * @return string representing the formated message.
	 */
	public String build();

}
