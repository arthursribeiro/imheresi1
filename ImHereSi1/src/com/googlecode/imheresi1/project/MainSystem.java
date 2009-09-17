package com.googlecode.imheresi1.project;
import java.io.IOException;
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
	private ArrayList<User> createdUsers;
	private PersistenceManager persistenceManager;
	private MessageController messageController;
	private Chat chat;

	//Map<userName, List<mails>>
	private Map<String,List<String>> invitations;

	public MainSystem() {
		this.persistenceManager = new PersistenceManagerImpl();
		this.messageController = new MessageController();
		this.invitations = new HashMap<String,List<String>>();
		this.loggedUsers = new HashMap<String,User>();		
		this.createdUsers = new ArrayList<User>();
	}

	public void confirmSharing(String from, String with, int mode) throws Exception{
		if(!this.invitations.containsKey(with)) throw new Exception("Convite nao foi enviado.");
		if(!this.loggedUsers.containsKey(from)) throw new Exception("Permissao negada.");

		User f = this.loggedUsers.get(from);
		User w = this.getUserByUserName(with);

		if(!this.invitations.get(with).contains(f.getMail())) throw new Exception("Convite nao foi enviado.");
	
		f.addFriend(w.getPublicInfo(),2);
		w.addFriend(f.getPublicInfo(),mode);
/*		
		System.out.println("------------");
		System.out.println(f);
		System.out.println(w);
		System.out.println("------------");
*/		
		this.invitations.get(with).remove(f.getMail());
	}

	public void refuseSharing(String from, String with) throws Exception{
		if(!this.invitations.containsKey(with)) throw new Exception("Convite nao foi enviado.");
		if(!this.loggedUsers.containsKey(from)) throw new Exception("Permissao negada.");

		User f = this.loggedUsers.get(from);

		if(!this.invitations.get(with).contains(f.getMail())) throw new Exception("Convite nao foi enviado.");

		this.invitations.get(with).remove(f.getMail());
	}

	public String getFriends(String userName) throws Exception{
		if(!this.loggedUsers.containsKey(userName)) throw new Exception("Permissao negada.");
		User u = getUserByUserName(userName);
		return u.getFriendsNames();
	}

	public String logIn(String userName, String password, String ip) throws Exception{
		User userToLogIn = this.getCreatedUserByUserName(userName);

		if(userToLogIn == null){
			if(!persistenceManager.hasUser(userName))
				throw new Exception("Login/senha invalidos.");

			userToLogIn = persistenceManager.getUserByUserName(userName);
		}
		
		userToLogIn.setIp(ip);
		
		if(!userToLogIn.getPassword().equals(password))
			throw new Exception("Login/senha invalidos.");


//		Quando for encerrar o sistema, deve-se salvar os usuarios do mapa e do array!		
//		if(this.createdUsers.contains(userToLogIn)) 
//			this.createdUsers.remove(userToLogIn);

		loggedUsers.put(userToLogIn.getUserName(), userToLogIn);


		
		return userToLogIn.getUserName();
	}
	
	public boolean isConected(String userName) {
		return this.loggedUsers.containsKey(userName);
	}

	public User createUser(String userName, String password, String email, String name, String phone) throws Exception{

		if((persistenceManager.hasUser(userName)) || (this.getCreatedUserByUserName(userName) != null)) 
			throw new Exception("O username jah existe.");

		User newUser = new User(userName, password);
		newUser.setMail(email);
		newUser.setName(name);
		newUser.setPhone(phone);

		this.createdUsers.add(newUser);

		return newUser;
	}

	public User getUserbyName(String name, int occurrence) throws Exception{
		User foundUser = this.getCreatedUserByName(name, occurrence);

		if( foundUser != null)return foundUser; 

		foundUser = persistenceManager.getUserByName(name, occurrence);
		if(foundUser == null) throw new Exception("O usuario nao existe.");

		return foundUser;
	}

	public User getUserByUserName(String userName) throws Exception{
		User foundUser = this.getCreatedUserByUserName(userName);

		if(foundUser != null) return foundUser;

		foundUser = persistenceManager.getUserByUserName(userName);
		if(foundUser == null) throw new Exception("O usuario nao existe.");

		return foundUser;
	}
	//Accessory Methods for created users - One might be created and don`t logIn.
	//It`s important to save them when the system might be closed.

	private User getCreatedUserByName(String name, int occurance){
		int found = 0;

		for(int i = 0; i < this.createdUsers.size(); i++){
			if(this.createdUsers.get(i).getName().contains(name)) found++;
			if(found == occurance) return this.createdUsers.get(i);
		}
		return null;
	}

	private User getCreatedUserByUserName(String userName){
		for(User usuario: this.createdUsers){
			if(usuario.getUserName().equals(userName))
				return usuario;
		}

		return null;
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
		this.persistenceManager.saveUser(this.getUserByUserName(userName), userName);
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

	public void resetBD() {
		persistenceManager.resetBD();		
	}

	public void saveUser(User user) throws IOException {
		persistenceManager.saveUser(user, user.getUserName());
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
	
	public void removeAllFriends(User user) throws Exception{
		User auxUser;
		for(PublicInfo pInfo: user.getFriendsPublicInfo()){
			auxUser = this.getUserByUserName(pInfo.getLogin());
			auxUser.removeFriend(user.getUserName());
			this.persistenceManager.saveUser(auxUser, auxUser.getUserName());
		}
	}

	public void removeUser(String userName) throws Exception{
		User userToRemove = this.getUserByUserName(userName);
		this.removeAllFriends(userToRemove);

		if(this.createdUsers.contains(userToRemove)) 
			this.createdUsers.remove(userToRemove);

		this.persistenceManager.removeUser(userToRemove.getUserName());
	}

}