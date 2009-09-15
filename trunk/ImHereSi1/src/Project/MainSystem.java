package Project;
import java.util.HashMap;

public class MainSystem {
	
	private HashMap<String, User> loggedUsers;
	private PersistenceManager persistenceManager;
	
	public MainSystem(){
		persistenceManager = new PersistenceManagerImpl();
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
	
	
	public User getUserbyName(String name, int occurrence) throws Exception{
		User foundUser = persistenceManager.getUserByName(name, occurrence);
		if(foundUser == null) throw new Exception("O usuario nao existe.");
		
		return foundUser;
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


