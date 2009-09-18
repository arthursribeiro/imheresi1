package com.googlecode.imheresi1.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private MessageController messageController;
	private Chat chat;

	private Map<String, List<String>> invitations;

	/**
	 * Constructor
	 */
	public MainSystem() {
		this.persistenceManager = new PersistenceManagerImpl();
		this.messageController = new MessageController();
		this.invitations = new HashMap<String, List<String>>();
		this.loggedUsers = new HashMap<String, User>();
		this.createdUsers = new ArrayList<User>();
	}

	/**
	 * 
	 * @param from
	 * @param with
	 * @param mode
	 * @throws MainSystemException
	 * @throws IOException
	 * @throws UserException
	 */
	public void confirmSharing(String from, String with, int mode)
	throws MainSystemException, IOException, UserException {
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
	}

	public void setLocal(String userName, double latitude, double longitude) throws PositionException, MainSystemException, IOException{
		User user = this.getUserByUserName(userName);
		user.setPositionManual(latitude, longitude);
		this.loggedUsers.put(user.getUserName(), user);
		this.refreshMyLocalization(user, latitude, longitude);
		this.persistenceManager.saveUser(user, user.getUserName());
	}



	public void removeFriend(String userName, String friend) throws Exception{
		User user;
		try{
			user = this.getUserByUserName(userName);

		} catch (Exception ex){
			throw new Exception("Permissao negada.");
		}
		user.removeFriend(friend);
		this.persistenceManager.saveUser(user, user.getUserName());
	}

	public void setSharing(String userName, String friend, int mode) throws Exception{
		User user = this.getUserByUserName(friend);
		user.setSharingOption(userName, mode);
		this.persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * 
	 * @param from
	 * @param with
	 * @param mode
	 * @throws MainSystemException
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
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws MainSystemException
	 * @throws IOException
	 */
	public String getFriends(String userName) throws MainSystemException,
	IOException {
		if (!this.loggedUsers.containsKey(userName))
			throw new MainSystemException("Permissao negada.");
		User u = getUserByUserName(userName);
		return u.getFriendsNames();
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param ip
	 * @return
	 * @throws UserException
	 * @throws IOException
	 */
	public String logIn(String userName, String password, String ip)
	throws UserException, IOException, PositionException {
		User userToLogIn = this.getCreatedUserByUserName(userName);

		if (userToLogIn == null) {
			if (!persistenceManager.hasUser(userName))
				throw new UserException("Login/senha invalidos.");

			userToLogIn = persistenceManager.getUserByUserName(userName);
		}

		if(userToLogIn.willChangeIp(ip)){
			userToLogIn.setIp(ip);
			this.refreshMyLocalization(userToLogIn, ip);
		}

		if (!userToLogIn.getPassword().equals(password))
			throw new UserException("Login/senha invalidos.");

		loggedUsers.put(userToLogIn.getUserName(), userToLogIn);

		return userToLogIn.getUserName();
	}

	private void refreshMyLocalization(User user, double latitude, double longitude)
	throws PositionException, IOException{
		for(PublicInfo friendInfo : user.getFriendsPublicInfo()){
			User friend = this.persistenceManager.
			getUserByUserName(friendInfo.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setPositionManual(latitude, longitude);
			this.saveUser(friend);
		}
	}
	
	private void refreshMyLocalization(User user, String ip)
	throws PositionException, IOException{
		for(PublicInfo friendInfo : user.getFriendsPublicInfo()){
			User friend = this.persistenceManager.
			getUserByUserName(friendInfo.getLogin());
			PublicInfo pInfo = friend.getAFriendPublicInfo(user.getUserName());
			pInfo.setPosition(ip);
			this.saveUser(friend);
		}
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public boolean isConected(String userName) {
		return this.loggedUsers.containsKey(userName);
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param email
	 * @param name
	 * @param phone
	 * @return
	 * @throws MainSystemException
	 * @throws UserException
	 */
	public User createUser(String userName, String password, String email,
			String name, String phone) throws MainSystemException,
			UserException {

		if ((persistenceManager.hasUser(userName))
				|| (this.getCreatedUserByUserName(userName) != null))
			throw new MainSystemException("O username jah existe.");

		User newUser = new User(userName, password);
		newUser.setMail(email);
		newUser.setName(name);
		newUser.setPhone(phone);

		this.createdUsers.add(newUser);

		return newUser;
	}

	/**
	 * 
	 * @param name
	 * @param occurrence
	 * @return
	 * @throws Exception
	 */
	public User getUserbyName(String name, int occurrence) throws Exception {
		User foundUser = this.getCreatedUserByName(name, occurrence);

		if (foundUser != null)
			return foundUser;

		foundUser = persistenceManager.getUserByName(name, occurrence);
		if (foundUser == null)
			throw new MainSystemException("O usuario nao existe.");

		return foundUser;
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws MainSystemException
	 * @throws IOException
	 */
	public User getUserByUserName(String userName) throws MainSystemException,
	IOException {
		User foundUser = this.getCreatedUserByUserName(userName);

		if (foundUser != null)
			return foundUser;
		//		System.out.println("RAQUEL");
		foundUser = persistenceManager.getUserByUserName(userName);
		if (foundUser == null)
			throw new MainSystemException("O usuario nao existe.");

		return foundUser;
	}

	/**
	 * 
	 * @param name
	 * @param occurance
	 * @return
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
	 * 
	 * @param userName
	 * @return
	 */
	private User getCreatedUserByUserName(String userName) {
		for (User usuario : this.createdUsers) {
			if (usuario.getUserName().equals(userName))
				return usuario;
		}

		return null;
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @throws MainSystemException
	 * @throws MessageControllerException
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
		Message m = new Invitation(u.getName(), u.getMail(), to);
		this.messageController.sendMessage(m);
	}

	/**
	 * 
	 * @param userName
	 * @throws MainSystemException
	 * @throws IOException
	 */
	public void logOut(String userName) throws MainSystemException, IOException {
		if (!loggedUsers.containsKey(userName))
			throw new MainSystemException("Sessao inexistente.");
		this.persistenceManager.saveUser(this.getUserByUserName(userName),
				userName);
		this.loggedUsers.remove(userName);
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MainSystemException
	 * @throws MessageControllerException
	 * @throws IOException
	 */
	public void sendMail(String from, String to, String subject, String msg)
	throws MainSystemException, MessageControllerException, IOException {
		User sender = getUserByUserName(from);
		User receiver = getUserByUserName(to);
		Message mail = new Email(sender.getMail(), receiver.getMail(), subject,
				msg);
		messageController.sendMessage(mail);
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param msg
	 * @throws MainSystemException
	 * @throws MessageControllerException
	 * @throws IOException
	 */
	public void sendSMS(String from, String to, String msg)
	throws MainSystemException, MessageControllerException, IOException {
		User sender = getUserByUserName(from);
		User receiver = getUserByUserName(to);
		Message sms = new SMS(sender.getName(), receiver.getPhone(), msg);
		messageController.sendMessage(sms);
	}

	/**
	 * 
	 * @param u1
	 * @param u2
	 */
	public void initChat(String u1, String u2) {
		chat = new Chat(u1, u2);
	}

	/**
	 * 
	 * @throws MessageControllerException
	 */
	public void endChat() throws Exception {
		messageController.sendMessage(chat);
		chat = null;
	}

	/**
	 * 
	 * @param receiver
	 * @param msg
	 * @throws MainSystemException
	 */
	public void sendChat(String receiver, String msg) throws Exception {
		if (chat == null)
			throw new Exception("Chat nao foi iniciado.");
		chat.addMsg(receiver, msg);
	}

	/**
	 * resets the database
	 */
	public void resetBD() {
		persistenceManager.resetBD();
		this.loggedUsers.clear();
		this.createdUsers.clear();
	}

	/**
	 * 
	 * @param user
	 */
	public void saveUser(User user) throws IOException {
		persistenceManager.saveUser(user, user.getUserName());
	}

	/**
	 * 
	 * @param userName
	 * @param valor
	 * @throws Exception
	 */
	public void updateName(String userName, String valor) throws Exception {
		User user = this.getUserByUserName(userName);
		user.setName(valor);
	}

	/**
	 * 
	 * @param userName
	 * @param valor
	 * @throws MainSystemException
	 * @throws UserException
	 * @throws IOException
	 */
	public void updateMail(String userName, String valor)
	throws MainSystemException, UserException, IOException {
		User user = this.getUserByUserName(userName);
		user.setMail(valor);
	}

	/**
	 * 
	 * @param userName
	 * @throws MainSystemException
	 * @throws IOException
	 */
	public void updatePhone(String userName, String valor)
	throws MainSystemException, IOException {
		User user = this.getUserByUserName(userName);
		user.setPhone(valor);
	}

	/**
	 * 
	 * @param user
	 * @throws UserException
	 * @throws MainSystemException
	 * @throws IOException
	 */
	public void removeAllFriends(User user) throws UserException,
	MainSystemException, IOException {
		User auxUser;
		for (PublicInfo pInfo : user.getFriendsPublicInfo()) {
			auxUser = this.getUserByUserName(pInfo.getLogin());
			auxUser.removeFriend(user.getUserName());
			this.persistenceManager.saveUser(auxUser, auxUser.getUserName());
		}
	}

	public void exitSystem(){
		for(User user : this.createdUsers){
			this.persistenceManager.saveUser(user, user.getUserName());
		}
		for(String userName : this.loggedUsers.keySet()){
			this.persistenceManager.saveUser(this.loggedUsers.get(userName), userName);
		}

		this.createdUsers.clear();
		this.loggedUsers.clear();
	}

	/**
	 * 
	 * @param userName
	 * @throws MainSystemException
	 * @throws UserException
	 * @throws IOException
	 * @throws PersistenceManagerException
	 */
	public void removeUser(String userName) throws MainSystemException,
	UserException, IOException {
		User userToRemove = this.getUserByUserName(userName);
		this.removeAllFriends(userToRemove);

		if (this.createdUsers.contains(userToRemove))
			this.createdUsers.remove(userToRemove);

		try {
			if(this.persistenceManager.hasUser(userName))
				this.persistenceManager.removeUser(userToRemove.getUserName());
		} catch (PersistenceManagerException e) {
			throw new MainSystemException("O usuario nao existe.");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}