package com.googlecode.imheresi1.project;

import java.io.IOException;

/**
 * Class that implements the PersistenceManager type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public interface PersistenceManager {

	boolean hasUser(String user);

	User getUserByName(String name, int occurrence) throws Exception;

	User getUserByUserName(String userName) ;

	void resetBD();

	void saveUser(User user, String userName);

	void removeUser(String userName) throws PersistenceManagerException;

}
