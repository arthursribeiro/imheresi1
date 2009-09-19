package com.googlecode.imheresi1.project;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

/**
 * Class that handles the PersistenceManagerImpl type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class PersistenceManagerImpl implements PersistenceManager {

	XStream xstream = new XStream();

	/**
	 * 
	 * @param user
	 * @return boolean representing if the user exists or not
	 */
	public boolean hasUser(String user) {
		try {
			FileInputStream a = new FileInputStream("files/users/" + user
					+ ".xml");
			try {
				a.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	/**
	 * 
	 */
	public void clearInvitations() {
		File file = new File("files/invitation/invitation.xml");
		file.delete();
	}

	/**
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getInvitations() {
		FileReader reader;
		try {
			reader = new FileReader("files/invitation/invitation.xml");
			Map<String, List<String>> returnUser = (Map<String, List<String>>) xstream
					.fromXML(reader);
			reader.close();
			return returnUser;
		} catch (FileNotFoundException e1) {
			// e1.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param name
	 *            - Name to be searched
	 * @param occurrence
	 *            - Name occurrence in the users
	 * @exception Exception
	 */
	public User getUserByName(String name, int occurrence) {
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<String> names = new ArrayList<String>();

		File file = new File("files/users");

		for (int i = 0; i < file.list().length; i++) {
			try {
				FileReader reader = new FileReader("files/users/"
						+ file.list()[i]);
				User a = (User) xstream.fromXML(reader);
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				users.add(a);
			} catch (FileNotFoundException e) {
				// System.err.println("File not found");;
			}
		}

		for (User i : users) {
			names.add(i.getName());
		}
		Object[] sorted = names.toArray();
		Arrays.sort(sorted);
		int occ = 0;
		for (Object i : sorted) {
			for (User u : users) {
				if (u.getName().equals(i)) {
					occ++;
				}
				if (occ == occurrence) {
					return u;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param userName
	 *            - That will represent the xml file name
	 * @return User that represents the user
	 */
	public User getUserByUserName(String userName) {
		FileReader reader;
		try {
			reader = new FileReader("files/users/" + userName + ".xml");
			User returnUser = (User) xstream.fromXML(reader);
			reader.close();
			return returnUser;
		} catch (FileNotFoundException e1) {
			// e1.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 */
	public void resetBD() {
		File file = new File("files/users");

		for (String i : file.list()) {
			File del = new File("files/users/" + i);
			del.delete();
		}
	}

	/**
	 * 
	 * @param user
	 *            - User that contains User information
	 * @param userName
	 *            - String that will represent the file name
	 * @throws Exception
	 */
	public void saveUser(User user, String userName) {
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(new FileOutputStream("files/users/"
					+ userName + ".xml"));
			xstream.toXML(user, dos);
			dos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param userName
	 * 
	 */
	public void removeUser(String userName) throws PersistenceManagerException {
		if (hasUser(userName)) {
			File file = new File("files/users/" + userName + ".xml");
			file.delete();
			return;
		}
		throw new PersistenceManagerException("File doesn't exist");
	}

	/**
	 * @param invitations
	 */
	public void saveInvitations(Map<String, List<String>> invitations) {
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(new FileOutputStream("files/invitation/"
					+ "invitation.xml"));
			xstream.toXML(invitations, dos);
			dos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}