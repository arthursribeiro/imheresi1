package Project;

public interface PersistenceManager {

	boolean hasUser(String user);

	User getUser(String user);

	User getUserByName(String name, int occurrence);

	User getUserByUserName(String userName);

	void resetBD();

	void saveUser(User user);

	void removeUser(User userById);

}
