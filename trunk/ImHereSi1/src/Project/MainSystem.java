package Project;
import java.util.HashMap;

import Message.Chat;
import Message.Email;
import Message.Message;
import Message.MessageController;
import Message.SMS;

public class MainSystem {
	
	private HashMap<String, User> loggedUsers;
	private PersistenceManager persistenceManager;
	private MessageController messageController;
	private Chat chat;
	
	public MainSystem(){
		persistenceManager = new PersistenceManagerImpl();
		messageController = new MessageController();
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


