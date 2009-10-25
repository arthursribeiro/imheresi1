package com.googlecode.imheresi1.project;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Encoder;

import com.googlecode.imheresi1.localization.PositionException;
import com.googlecode.imheresi1.message.Chat;
import com.googlecode.imheresi1.message.Email;
import com.googlecode.imheresi1.message.Invitation;
import com.googlecode.imheresi1.message.Message;
import com.googlecode.imheresi1.message.MessageController;
import com.googlecode.imheresi1.message.MessageControllerException;
import com.googlecode.imheresi1.message.SMS;

/**
 * Class that implements the MainSystem type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class MainSystem {

	public static final int OCULTAR = 1;
	public static final int EXIBIR = 2;

	private HashMap<String, User> loggedUsers;
	private ArrayList<User> createdUsers;
	private PersistenceManager persistenceManager;
	private Chat chat;
	private String invitationsDirectory = "";

	private Map<String, List<String>> invitations;

	/**
	 * Constructor
	 */
	public MainSystem() {
		this.persistenceManager = new PersistenceManagerImpl();
		this.invitations = this.persistenceManager.getInvitations();
		if (this.invitations == null)
			this.invitations = new HashMap<String, List<String>>();
		this.loggedUsers = new HashMap<String, User>();
		this.createdUsers = new ArrayList<User>();
	}

	/**
	 * Method to set the directory in the System where the invitations predetermined text is stored
	 * @param value - string representing the path to the directory
	 */
	public void setDirectory(String value) {
		this.invitationsDirectory = value;
	}

	/**
	 * Method to get the friend position given a userName and the friend's userName   
	 * @param userName - string representing the userName of the user
	 * @param userNameToLocalize - string representing the userName of the user's friend
	 * @return string - formated string representing the location of the user userNameToLocalize 
	 * @throws MainSystemException if the user does not exist
	 * @throws PositionException if the position was not possible to obtain
	 * @throws UserException if the given userName is not a friend
	 */
	public String getAFriendPosition(String userName,
			String userNameToLocalize) throws MainSystemException, UserException, PositionException {
		User user = this.getUserByUserName(userName);
		if(user.getFriendLocation(userNameToLocalize) == null) return null;
		return user.getFriendLocation(userNameToLocalize).toString();
	}

	/**
	 * Method to return a formatted string containing the invitations sent to a specific user
	 * @param username - string representing the user
	 * @return string - formatted string
	 * @throws MainSystemException if the user does not exist
	 */
	public String toStringMyInvitations(String username) throws MainSystemException {
		User user;
		List<String> map = null;
		
		user = this.getUserByUserName(username);
		map = getInvitationList(user.getMail());

		String separator = System.getProperty("line.separator");
		String saida = "================================================================="
				+ separator
				+ "Username                  Nome                            "
				+ separator
				+ "================================================================="
				+ separator;

		if (map.size() == 0)
			return "";

		for (int i = 0; i < map.size(); i++) {
			String userName = map.get(i);
			User u;
			u = this.getUserByUserName(userName);
			saida += userName + "                   " + u.getName();
		}
		return saida;
	}

	/**
	 * Method that returns the list of invitations sent to a specific e-mail 
	 * @param mail - string representing the user's e-mail
	 * @return List<String> - List of userName's who sent an invitation to the e-mail
	 */
	private List<String> getInvitationList(String mail) {
		List<String> map = new ArrayList<String>();

		for (String username : this.invitations.keySet()) {
			if (this.invitations.get(username).contains(mail))
				map.add(username);
		}

		return map;
	}

	/**
	 * Method to confirm sharing between two user's given that an invitation was sent between the user's
	 * @param from - string representing the user who sent the invitation
	 * @param with - string representing the user who received the invitation
	 * @param mode - mode of sharing (1: Not Visible, 2: Visible)
	 * @throws MainSystemException if the invitation was not sent
	 * @throws UserException if the user's are already friends
	 */
	public void confirmSharing(String from, String with, int mode)
			throws MainSystemException, UserException {
		if (!this.invitations.containsKey(with))
			throw new MainSystemException("Convite nao foi enviado.");
		if (!this.loggedUsers.containsKey(from))
			throw new MainSystemException("Permissao negada.");

		User f = this.loggedUsers.get(from);
		User w = this.getUserByUserName(with);

		if (!this.invitations.get(with).contains(f.getMail()))
			throw new MainSystemException("Convite nao foi enviado.");

		f.addFriend(w.getPublicInfo(), 2);
		w.addFriend(f.getPublicInfo(), mode);

		this.invitations.get(with).remove(f.getMail());
		this.persistenceManager.saveUser(w, w.getUserName());
		this.persistenceManager.saveUser(f, f.getUserName());
		this.persistenceManager.saveInvitations(invitations);
	}

	/**
	 * Method to set the position of the user through an IP.
	 * @param userName - string representing the user's userName
	 * @param ip - string representing the IP to set the user's position
	 * @throws MainSystemException if the user does not exist
	 * @throws PositionException if the values ate not valid or could not find the GeoIP database
	 */
	public void setLocal(String userName, String ip)
			throws MainSystemException, PositionException {
		User user = this.getUserByUserName(userName);
		user.setPosition();
		this.loggedUsers.put(user.getUserName(), user);
		this.refreshMyLocalization(user, ip);
		this.persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * Method to set the position of the user through values representing the latitude and longitude.
	 * @param userName - string representing the user's userName 
	 * @param latitude - latitude value
	 * @param longitude - longitude value
	 * @throws MainSystemException if the user does not exist
	 * @throws PositionException if the values ate not valid or could not find the GeoIP database
	 */
	public void setLocal(String userName, double latitude, double longitude)
			throws PositionException, MainSystemException {
		User user = this.getUserByUserName(userName);
		user.setPositionManual(latitude, longitude);
		this.loggedUsers.put(user.getUserName(), user);
		this.refreshMyLocalization(user, latitude, longitude);
		this.persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * Method to remove a friendship connection between two user's
	 * @param userName - string representing the user's userName
	 * @param friend - string representing the friend's userName
	 * @throws MainSystemException if the user does not exist
	 * @throws UserException if friend is not userName's friend.
	 */
	public void removeFriend(String userName, String friend) throws MainSystemException, UserException  {
		User user;
		user = this.getUserByUserName(userName);
		user.removeFriend(friend);
		this.persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * Method to set the sharing mode of userName in relation to friend
	 * @param userName - string representing the user's userName
	 * @param friend - string representing the friend's userName
	 * @param mode - mode of sharing (1: Not Visible, 2: Visible)
	 * @throws MainSystemException if the user does not exist 
	 * @throws UserException if the friend's userName is not a friend of the User's object
	 */
	public void setSharing(String userName, String friend, int mode) throws MainSystemException, UserException {
		User user = this.getUserByUserName(friend);
		user.setSharingOption(userName, mode);
		this.persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * Method to refuse sharing between two user's given that an invitation was sent between the user's
	 * @param from - string representing the user who sent the invitation
	 * @param with - string representing the user who received the invitation
	 * @throws MainSystemException if the invitation was not sent
	 */
	public void refuseSharing(String from, String with)
			throws MainSystemException {
		if (!this.invitations.containsKey(with))
			throw new MainSystemException("Convite nao foi enviado.");
		if (!this.loggedUsers.containsKey(from))
			throw new MainSystemException("Permissao negada.");

		User f = this.loggedUsers.get(from);

		if (!this.invitations.get(with).contains(f.getMail()))
			throw new MainSystemException("Convite nao foi enviado.");

		this.invitations.get(with).remove(f.getMail());
		this.persistenceManager.saveInvitations(invitations);
	}

	/**
	 * Method that returns a list of names, representing the friends of a given user
	 * @param userName - string representing the user's userName
	 * @return string - list of names in a formatted manner
	 * @throws MainSystemException if the user does not exist 
	 */
	public String getFriends(String userName) throws MainSystemException {
		if (!this.loggedUsers.containsKey(userName))
			throw new MainSystemException("Permissao negada.");
		User u = getUserByUserName(userName);
		return u.getFriendsNames();
	}

	/**
	 * Method to log in a user in the System
	 * @param userName - string representing the userName to login
	 * @param password - string representing the user's password
	 * @param ip - string representing the IP
	 * @return string - string representing the logged userName
	 * @throws UserException if the string represents a invalid IP or login and password do not match
	 * @throws PositionException if the values ate not valid or could not find the GeoIP database
	 */
	public String logIn(String userName, String password, String ip)
			throws UserException, PositionException {
		User userToLogIn = this.getCreatedUserByUserName(userName);

		if (userToLogIn == null) {
			if (!persistenceManager.hasUser(userName))
				throw new UserException("Login/senha invalidos.");
			userToLogIn = persistenceManager.getUserByUserName(userName);
		}

		if (userToLogIn.willChangeIp(ip)) {
			userToLogIn.setIp(ip);
			this.refreshMyLocalization(userToLogIn, ip);
		}

		if (password == null || !userToLogIn.getPassword().equals(encripta(password)))
			throw new UserException("Login/senha invalidos.");

		loggedUsers.put(userToLogIn.getUserName(), userToLogIn);
		return userToLogIn.getUserName();
	}
	
	/**
	 * Method to encrypt a given string
	 * @param password - string to be encrypted
	 * @return string - encrypted string
	 */
	private static String encripta(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(password.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(digest.digest());
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
			return password;
		}
	}
	
	/**
	 * Method the update a given user's localization given the latitude and longitude values
	 * @param user - user to have the localization updated
	 * @param latitude - latitude value
	 * @param longitude - longitude value
	 * @throws PositionException if the values are invalid.
	 */
	private void refreshMyLocalization(User user, double latitude,
			double longitude) throws PositionException {
		for (PublicInfo friendInfo : user.getFriendsPublicInfo()) {
			User friend = this.persistenceManager.getUserByUserName(friendInfo
					.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setPositionManual(latitude, longitude);
			this.saveUser(friend);
		}
	}

	/**
	 * Method the update a given user's localization given the IP
	 * @param user - user to have the localization updated
	 * @param ip - string representing the IP
	 * @throws PositionException if the values ate not valid or could not find the GeoIP database
	 */
	private void refreshMyLocalization(User user, String ip)
			throws PositionException {
		for (PublicInfo friendInfo : user.getFriendsPublicInfo()) {
			User friend = this.persistenceManager.getUserByUserName(friendInfo
					.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setPosition(ip);
			this.saveUser(friend);
		}
	}

	/**
	 * Method to identify if the user is logged in the System
	 * @param userName - string representing the userName to be checked
	 * @return boolean - true if the user is logged, false otherwise
	 */
	public boolean isConected(String userName) {
		return this.loggedUsers.containsKey(userName);
	}

	/**
	 * Method to create a new user in the system
	 * @param userName - string representing the new userName
	 * @param password - string representing the new user's password
	 * @param email - string representing the new user's email
	 * @param name - string representing the new user's name
	 * @param phone - string representing the new user's phone number
	 * @return
	 * @throws MainSystemException
	 * @throws UserException
	 */
	public void createUser(String userName, String password, String email,
			String name, String phone) throws MainSystemException,
			UserException, IOException {

		if ((persistenceManager.hasUser(userName))
				|| (this.getCreatedUserByUserName(userName) != null))
			throw new MainSystemException("O username jah existe.");

		User newUser = new User(userName, password);
		newUser.setMail(email);
		newUser.setName(name);
		newUser.setPhone(phone);

		this.createdUsers.add(newUser);

	}

	/**
	 * Method that gets a User Object found by the given name and occurrence.
	 * The occurrence value determines which user to return in case of multiple users in which the names match the parameter name
	 * @param name - string representing the searched user's name 
	 * @param occurrence - number to determine the user to return
	 * @return User - user object representing the user found.
	 * @throws MainSystemException if the user does not exist
	 */
	public User getUserbyName(String name, int occurrence) throws MainSystemException {
		User foundUser = this.getCreatedUserByName(name, occurrence);

		if (foundUser != null)
			return foundUser;

		foundUser = persistenceManager.getUserByName(name, occurrence);
		if (foundUser == null)
			throw new MainSystemException("O usuario nao existe.");

		return foundUser;
	}

	/**
	 * Method that gets a User Object found by the given userName
	 * @param userName - string representing the searched user's userName 
	 * @return User - user object representing the user found.
	 * @throws MainSystemException if the user does not exist
	 */
	public User getUserByUserName(String userName) throws MainSystemException {
		User foundUser = this.getCreatedUserByUserName(userName);

		if (foundUser != null)
			return foundUser;

		foundUser = persistenceManager.getUserByUserName(userName);
		if (foundUser == null)
			throw new MainSystemException("O usuario nao existe.");

		return foundUser;
	}

	/**
	 * Method to obtain a created user given a name and occurrence
	 * The occurrence value determines which user to return in case of multiple users in which the names match the parameter name
	 * @param name - string representing the searched user's name 
	 * @param occurance - number to determine the user to return
	 * @return User - user object representing the user found.
	 */
	private User getCreatedUserByName(String name, int occurance) {
		int found = 0;

		for (int i = 0; i < this.createdUsers.size(); i++) {
			if (this.createdUsers.get(i).getName().contains(name))
				found++;
			if (found == occurance)
				return this.createdUsers.get(i);
		}
		return null;
	}

	/**
	 * Method to obtain a created user given a userName
	 * @param userName - string representing the searched user's userName 
	 * @return User - user object representing the user found.
	 */
	private User getCreatedUserByUserName(String userName) {
		for (User usuario : this.createdUsers) {
			if (usuario.getUserName().equals(userName))
				return usuario;
		}
		return null;
	}

	/**
	 * Method to sent an invitation from a given user to a given email
	 * @param from - string representing the user's userName
	 * @param to - string representing the email who will receive the invitation
	 * @throws MainSystemException if the user is not logged
	 * @throws MessageControllerException if the message was not possible to be sent
	 */
	public void sendInvitation(String from, String to)
			throws MainSystemException, MessageControllerException {
		if (!this.loggedUsers.containsKey(from))
			throw new MainSystemException("Permissao negada.");
		if (this.invitations.containsKey(from))
			this.invitations.get(from).add(to);
		else {
			List<String> mails = new ArrayList<String>();
			mails.add(to);
			this.invitations.put(from, mails);
		}
		User u = this.loggedUsers.get(from);

		Message m = new Invitation(u.getName(), u.getMail(), to, this.invitationsDirectory);
		this.persistenceManager.saveInvitations(this.invitations);
		MessageController.sendMessage(m);
	}

	/**
	 * Method to log out a user from the System
	 * @param userName - string representing the user's userName
	 * @throws MainSystemException if the user does not exist or was not logged
	 */
	public void logOut(String userName) throws MainSystemException {
		if (!loggedUsers.containsKey(userName))
			throw new MainSystemException("Sessao inexistente.");
		this.persistenceManager.saveUser(this.getUserByUserName(userName),
				userName);
		this.loggedUsers.remove(userName);
	}

	/**
	 * Method to send a email between two users
	 * @param from - string representing the userName of the user who will send the email
	 * @param to - string representing the userName of the user who will receive the email
	 * @param subject - subject of the email
	 * @param msg - email message
	 * @throws MainSystemException if the user does not exist
	 * @throws MessageControllerException if the message was not possible to be sent
	 */
	public void sendMail(String from, String to, String subject, String msg)
			throws MainSystemException, MessageControllerException {
		User sender = getUserByUserName(from);
		User receiver = getUserByUserName(to);
		Message mail = new Email(sender.getMail(), receiver.getMail(), subject,
				msg);
		MessageController.sendMessage(mail);
	}

	/**
	 * Method to send a SMS between two users
	 * @param from - string representing the userName of the user who will send the SMS
	 * @param to - string representing the userName of the user who will receive the SMS
	 * @param msg - SMS message
	 * @throws MainSystemException if the user does not exist or does not have a phone number registered in the System  
	 * @throws MessageControllerException if the message was not possible to be sent
	 */
	public void sendSMS(String from, String to, String msg)
			throws MainSystemException, MessageControllerException {
		User sender = getUserByUserName(from);
		User receiver = getUserByUserName(to);
		if (receiver.getPhone().equals(""))
			throw new MainSystemException("Numero de telefone nao encontrado.");
		Message sms = new SMS(sender.getName(), receiver.getPhone(), msg);
		MessageController.sendMessage(sms);
	}

	/**
	 * Method to initiate a Chat between two users
	 * @param u1 - string representing a user in the Chat
	 * @param u2 - string representing the other user in the Chat
	 */
	public void initChat(String u1, String u2) {
		chat = new Chat(u1, u2);
	}

	/**
	 * Method to end a chat
	 * @throws MessageControllerException if the chat log could not be saved in the System 
	 */
	public void endChat() throws MessageControllerException {
		MessageController.sendMessage(chat);
		chat = null;
	}

	/**
	 * Method to send a message in the current chat
	 * @param receiver - string representing the user who received the message
	 * @param msg - string representing the user who sent the message
	 * @throws MainSystemException if the chat was not initiated
	 */
	public void sendChat(String receiver, String msg) throws MainSystemException  {
		if (chat == null)
			throw new MainSystemException("Chat nao foi iniciado.");
		chat.addMsg(receiver, msg);
	}

	/**
	 * Method to receive a message in the current chat
	 * @param user - string representing the user
	 * @throws MainSystemException if the chat was not initiated
	 */
	public String receiveChat(String user) throws MainSystemException {
		if (chat == null)
			throw new MainSystemException("Chat nao foi iniciado.");
		return chat.getLastMessage(user);
	}
	
	/**
	 * resets the database
	 */
	public void resetBD() {
		persistenceManager.resetBD();
		persistenceManager.clearInvitations();
		this.loggedUsers.clear();
		this.createdUsers.clear();
	}

	/**
	 * Method to save a user in the System's database
	 * If the userName already exists: overrides the file. Otherwise it creates a new file.
	 * @param user - User Object that contains the data to be saved.
	 */
	public void saveUser(User user) {
		persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * Method to update a given user's name
	 * @param userName - string representing the user's userName
	 * @param newName - string representing the new name
	 * @throws UserException if no name or an invalid one is passed
	 * @throws MainSystemException if the user does not exist
	 * 
	 */
	public void updateName(String userName, String newName)
			throws UserException, MainSystemException {
		User user = this.getUserByUserName(userName);
		user.setName(newName);
		
		for (PublicInfo friendInfo : user.getFriendsPublicInfo()) {
			User friend = this.persistenceManager.getUserByUserName(friendInfo
					.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setName(newName);
			this.saveUser(friend);
		}		
		
		this.persistenceManager.saveUser(user, userName);
		this.loggedUsers.put(user.getUserName(), user);
	}

	/**
	 * Method to update a given user's email
	 * @param userName - string representing the user's userName
	 * @param newMail - string representing the new email
	 * @throws MainSystemException if the user does not exist
	 * @throws UserException if no mail or an invalid one is passed
	 */
	public void updateMail(String userName, String newMail)
			throws MainSystemException, UserException {
		User user = this.getUserByUserName(userName);
		user.setMail(newMail);
		
		for (PublicInfo friendInfo : user.getFriendsPublicInfo()) {
			User friend = this.persistenceManager.getUserByUserName(friendInfo
					.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setEmail(newMail);
			this.saveUser(friend);
		}		
		
		this.persistenceManager.saveUser(user, userName);
		this.loggedUsers.put(user.getUserName(), user);
	}
	
	/**
	 * Method to update a given user's phone number
	 * @param userName - string representing the user's userName
	 * @param newPhoneNumber - string representing the new phone number
	 * @throws MainSystemException if the user does not exist
	 */
	public void updatePhone(String userName, String newPhoneNumber)
			throws MainSystemException {
		User user = this.getUserByUserName(userName);
		user.setPhone(newPhoneNumber);
		
		for (PublicInfo friendInfo : user.getFriendsPublicInfo()) {
			User friend = this.persistenceManager.getUserByUserName(friendInfo
					.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setTelephoneNumber(newPhoneNumber);
			this.saveUser(friend);
		}		
		
		this.persistenceManager.saveUser(user, userName);
		this.loggedUsers.put(user.getUserName(), user);
	}
	
	/**
	 * Method to update a given user's password
	 * @param userName - string representing the user's userName
	 * @param newPassword - string representing the new password
	 * @throws MainSystemException if the user does not exist
	 * @throws UserException if the password is invalid
	 */
	public void updatePass(String userName, String newPassword)
			throws MainSystemException, UserException {
		User user = this.getUserByUserName(userName);
		user.updatePassword(newPassword);
				
		this.persistenceManager.saveUser(user, userName);
		this.loggedUsers.put(user.getUserName(), user);
	}

	/**
	 * Method to remove all friends of a given user 
	 * @param user - User object to be removed all of his friends
	 * @throws UserException if tried to remove a friend who is not user's friend
	 * @throws MainSystemException if tried to remove a friend userName who does not exist
	 */
	private void removeAllFriends(User user) throws UserException,
			MainSystemException {
		User auxUser;
		for (PublicInfo pInfo : user.getFriendsPublicInfo()) {
			auxUser = this.getUserByUserName(pInfo.getLogin());
			auxUser.removeFriend(user.getUserName());
			this.persistenceManager.saveUser(auxUser, auxUser.getUserName());
		}
	}

	/**
	 * Method to exit the System
	 */
	public void exitSystem() {
		for (User user : this.createdUsers) {
			this.persistenceManager.saveUser(user, user.getUserName());
		}
		for (String userName : this.loggedUsers.keySet()) {
			this.persistenceManager.saveUser(this.loggedUsers.get(userName),
					userName);
		}

		this.persistenceManager.saveInvitations(invitations);

		this.createdUsers.clear();
		this.loggedUsers.clear();
	}

	/**
	 * Method to remove a user from the System
	 * @param userName - string representing the user's userName
	 * @throws MainSystemException if the user does not exist
	 */
	public void removeUser(String userName) throws MainSystemException{
		User userToRemove = this.getUserByUserName(userName);
		try {
			this.removeAllFriends(userToRemove);
		} catch (UserException e1) {
		//	e1.printStackTrace();
		}

		if (this.createdUsers.contains(userToRemove))
			this.createdUsers.remove(userToRemove);

		if (this.loggedUsers.containsKey(userName))
			this.loggedUsers.remove(userName);

		try {
			if (this.persistenceManager.hasUser(userName)) {
				this.persistenceManager.removeUser(userToRemove.getUserName());
			}

		} catch (PersistenceManagerException e) {
			throw new MainSystemException("O usuario nao existe.");
		}

	}

	/**
	 * Method to return a formatted string containing the users friends userNames
	 * @param userName - string representing the user's userName
	 * @return string - formatted string with the friends userNames
	 * @throws MainSystemException if the user does not exist
	 */
	public String getFriendsList(String userName) throws MainSystemException {
		User user = this.getUserByUserName(userName);
		return user.toStringFriends();
	}

	/**
	 * Method to check if an invitation was sent between two users
	 * @param userName - string representing the user who received the invitation
	 * @param uName - string representing the user who sent the invitation
	 * @return boolean - true if the invitation was sent, false otherwise
	 */
	public boolean hasInvitation(String userName, String uName) {
		try {
			User key = this.getUserByUserName(uName);
			User value = this.getUserByUserName(userName);

			if(this.invitations.containsKey(key.getUserName()) && this.invitations.get(key.getUserName()).contains(value.getMail())) return true;
			
		} catch (MainSystemException e) {
			//e.printStackTrace();
		}

		return false;
	}

}