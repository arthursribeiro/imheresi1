
public interface PersistenceManager {

	boolean hasUser(String user);

	User getUser(String user);

	SystemLog getSystemLog();

	User getUserByName(String name, int occurrence);

	User getUserById(int id);

	void resetBD();

	void saveUser(User user);

	void remvoeUser(User userById);

}
