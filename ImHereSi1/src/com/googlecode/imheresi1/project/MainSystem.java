package com.googlecode.imheresi1.project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.imheresi1.message.Chat;
import com.googlecode.imheresi1.message.Email;
import com.googlecode.imheresi1.message.Invitation;
import com.googlecode.imheresi1.message.Message;
import com.googlecode.imheresi1.message.MessageController;
import com.googlecode.imheresi1.message.SMS;


public class MainSystem {
	
	public static final int OCULTAR = 1;
	public static final int EXIBIR = 2;
	
	private HashMap<String, User> loggedUsers;
	private PersistenceManager persistenceManager;
	private MessageController messageController;
	private Chat chat;
	
	//Map<userName, List<mails>>
	private Map<String,List<String>> invitations;
	
	public MainSystem() {
		persistenceManager = new PersistenceManagerImpl();
		messageController = new MessageController();
		invitations = new HashMap<String,List<String>>();
		loggedUsers = new HashMap<String,User>();		
	}
	
	public void confirmSharing(String from, String with, int mode) throws Exception{
		if(!this.invitations.containsKey(with)) throw new Exception("Convite nao foi enviado.");
		if(!this.loggedUsers.containsKey(from)) throw new Exception("Permissao negada.");
		
		User f = this.loggedUsers.get(from);
		User w = getUserByUserName(with);
		
		if(!this.invitations.get(with).contains(f.getMail())) throw new Exception("Convite nao foi enviado.");

		f.addFriend(w.getPublicInfo(),mode);
		w.addFriend(f.getPublicInfo(),mode);
		
		this.invitations.get(with).remove(f.getMail());
	}
	
	public void refuseSharing(String from, String with, int mode) throws Exception{
		if(!this.invitations.containsKey(with)) throw new Exception("Convite nao foi enviado.");
		if(!this.loggedUsers.containsKey(from)) throw new Exception("Permissao negada.");
		
		User f = this.loggedUsers.get(from);
				
		if(!this.invitations.get(with).contains(f.getMail())) throw new Exception("Convite nao foi enviado.");

		this.invitations.get(with).remove(f.getMail());
	}
	
	public String getFriends(String userName) throws Exception{
		if(!this.loggedUsers.containsKey(userName)) throw new Exception("Permissao negada.");
		User u = getUserByUserName(userName);
		return u.getFriendsUserNames();
	}
	
	public String logIn(String userName, String password, String ip) throws Exception{
		if(!persistenceManager.hasUser(userName))
			throw new Exception("Login/senha invalidos.");
		
		User userToLogIn = persistenceManager.getUser(userName);
		
		if(!userToLogIn.getPassword().equals(password))
			throw new Exception("Login/senha invalidos.");
		
		userToLogIn.setIp(ip);
		loggedUsers.put(userToLogIn.getUserName(), userToLogIn);
		
		return userToLogIn.getUserName();
	}
	
	public User createUser(String userName, String password, String email, String name, String phone) throws Exception{

		if(persistenceManager.hasUser(userName)) throw new Exception("O username jah existe.");
	
		User newUser = new User(userName, password);
		newUser.setMail(email);
		newUser.setName(name);
		newUser.setPhone(phone);
	
		return newUser;
	}
	
	public void sendInvitation(String from, String to) throws Exception {
		if(!this.loggedUsers.containsKey(from)) throw new Exception("Permissao negada.");
		if(this.invitations.containsKey(from)) this.invitations.get(from).add(to);
		else {
			List<String> mails = new ArrayList<String>();
			mails.add(to);
			this.invitations.put(from, mails);
		}
		User u = this.loggedUsers.get(from);
		Message m = new Invitation(u.getName(),u.getMail(),to);
		this.messageController.sendMessage(m);
	}
	
	public void logOut(String userName) throws Exception {
		if(!loggedUsers.containsKey(userName)) throw new Exception("Sessao inexistente.");
		this.loggedUsers.remove(userName);
	}
	
	public void sendMail(String from, String to, String subject, String msg) throws Exception{
		User sender = getUserByUserName(from);
		User receiver = getUserByUserName(to);
		Message mail = new Email(sender.getMail(), receiver.getMail(), subject, msg);
		messageController.sendMessage(mail);
	}
	
	public void sendSMS(String from, String to, String msg) throws Exception{
		User sender = getUserByUserName(from);
		User receiver = getUserByUserName(to);
		Message sms = new SMS(sender.getName(), receiver.getPhone(), msg);
		messageController.sendMessage(sms);
	}
	
	public User getUserbyName(String name, int occurrence) throws Exception{
		User foundUser = persistenceManager.getUserByName(name, occurrence);
		if(foundUser == null) throw new Exception("O usuario nao existe.");
		
		return foundUser;
	}

	public void initChat(String u1, String u2){
		chat = new Chat(u1,u2);
	}
	
	public void endChat() throws Exception{
		messageController.sendMessage(chat);
		chat = null;
	}
	
	public void sendChat(String receiver, String msg) throws Exception{
		if(chat == null) throw new Exception("Chat nao foi iniciado.");
		chat.addMsg(receiver, msg);
	}
	
	public User getUserByUserName(String userName) throws Exception{
		User foundUser = persistenceManager.getUserByUserName(userName);
		if(foundUser == null) throw new Exception("O usuario nao existe.");
		
		return foundUser;
	}

	public void resetBD() {
		persistenceManager.resetBD();		
	}

	public void saveUser(User user) {
		persistenceManager.saveUser(user);
	}

	public void updateName(String userName, String valor) throws Exception{
		User user = this.getUserByUserName(userName);
		user.setName(valor);
	}

	public void updateMail(String userName, String valor) throws Exception{
		User user = this.getUserByUserName(userName);
		user.setMail(valor);
	}

	public void updatePhone(String userName , String valor) throws Exception{
		User user = this.getUserByUserName(userName);
		user.setPhone(valor);
	}

	public void removeUser(User userById) {
		persistenceManager.removeUser(userById);
	}
	
}