package Project;
import java.util.HashMap;

public class System {
	
	private HashMap<Integer, User> loggedUsers;
	private PersistenceManager persistenceManager;
	private SystemLog sysLog;
	private int lastId;
	
	public System(){
		persistenceManager = new PersistenceManagerImpl();
		sysLog = persistenceManager.getSystemLog();
		this.lastId = sysLog.getLastId();
	}
	
	public int logIn(String userName, String password, String ip) throws Exception{
		if(!persistenceManager.hasUser(userName))
			throw new Exception("Login/senha invalidos.");
		
		User userToLogIn = persistenceManager.getUser(userName);
		
		if(!userToLogIn.getPassword().equals(password))
			throw new Exception("Login/senha invalidos.");
		
		userToLogIn.setIp(ip);
		loggedUsers.put(userToLogIn.getId(), userToLogIn);
		
		return userToLogIn.getId();
	}
	
	public User creatUser(String userName, String password, String email, String name, String phone) throws Exception{
		if(persistenceManager.hasUser(userName)) throw new Exception("O username jah existe.");
		
		this.lastId = this.lastId + 1;
		
		User newUser = new User(userName, password, this.lastId);
		newUser.setMail(email);
		newUser.setName(name);
		newUser.setPhone(phone);
		newUser.setId(this.lastId);
		
		return newUser;
	}
	
	
	public User getUserbyName(String name, int occurrence) throws Exception{
		User foundUser = persistenceManager.getUserByName(name, occurrence);
		if(foundUser == null) throw new Exception("O usuario nao existe.");
		
		return foundUser;
	}

	public User getUserById(int id) throws Exception{
		User foundUser = persistenceManager.getUserById(id);
		if(foundUser == null) throw new Exception("O usuario nao existe.");
		
		return foundUser;
	}

	public void resetBD() {
		persistenceManager.resetBD();		
	}

	public void saveUser(User user) {
		persistenceManager.saveUser(user);
	}

	public void updateName(int id, String valor) throws Exception{
		User user = this.getUserById(id);
		user.setName(valor);
	}

	public void updateMail(int id, String valor) throws Exception{
		User user = this.getUserById(id);
		user.setMail(valor);
	}

	public void updatePhone(int id, String valor) throws Exception{
		User user = this.getUserById(id);
		user.setPhone(valor);
	}

	public void removeUser(User userById) {
		persistenceManager.remvoeUser(userById);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}


