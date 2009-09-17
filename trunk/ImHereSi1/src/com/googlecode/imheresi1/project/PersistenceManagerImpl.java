package com.googlecode.imheresi1.project;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.*;

public class PersistenceManagerImpl implements PersistenceManager {

	XStream xstream = new XStream();

	/**
	 * 
	 * @param user
	 * @return boolean representing if the user exists or not
	 */
	public boolean hasUser(String user) {
		try {
			FileInputStream a = new FileInputStream(user + ".xml");
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	public Object getUserByName(String name, int occurrence) {
		ArrayList<Object> users = new ArrayList<Object>();
		return null;
	}

	/**
	 * 
	 * @param userName - That will represent the xml file name
	 * @return Object that represents the user
	 */
	public Object getUserByUserName(String userName) throws IOException {
		FileReader reader = new FileReader(userName+".xml");
		Object returnObject = xstream.fromXML(reader);
		reader.close();
		return returnObject;
	}

	public void resetBD() {
		
	}

	/**
	 * 
	 * @param user - Object that contains User information
	 * @param userName - String that will represent the file name
	 */
	public void saveUser(Object user, String userName) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(userName+".xml"));
		xstream.toXML(user, dos);
		dos.close();
	}

	public void removeUser(String userName) {
		
	}
}
