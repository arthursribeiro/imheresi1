package com.googlecode.imheresi1.project;

public interface PersistenceManager {

	boolean hasUser(String user);

	Object getUser(String user);

	Object getUserByName(String name, int occurrence);

	Object getUserByUserName(String userName);

	void resetBD();

	void saveUser(Object user);

	void removeUser(String userName);

}
