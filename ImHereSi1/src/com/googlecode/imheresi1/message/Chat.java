package com.googlecode.imheresi1.message;

/**
 * Class that implements an interface Message and handles the Chat type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */

public class Chat implements Message {

	private String user1;
	private String user2;
	private String[] lastMessages;

	private StringBuilder sB;

	/**
	 * Constructor
	 * 
	 * @param u1
	 * @param u2
	 */
	public Chat(String u1, String u2) {
		this.user1 = u1;
		this.user2 = u2;
		sB = new StringBuilder();
		lastMessages = new String[2];
	}

	public String getMessage(String username){
		if(username.equals(user1)) return this.lastMessages[0];
		return this.lastMessages[1];
	}
	
	/**
	 * 
	 * @param receiver
	 * @param msg
	 */
	public void addMsg(String receiver, String msg) {
		if (receiver.equals(user1)){
			sB.append(user2);
			this.lastMessages[1] = msg;
		}else {
			sB.append(user1);
			this.lastMessages[0] = msg;
		}
		sB.append(": " + msg);
		sB.append(System.getProperty("line.separator"));
	}

	/**
	 * @return full message
	 */
	public String build() {
		return sB.toString();
	}

	/**
	 * @return path
	 */
	public String getPath() {
		if (user1.compareToIgnoreCase(user2) > 0)
			return "files/chats/" + user2 + "-" + user1 + ".log";
		return "files/chats/" + user1 + "-" + user2 + ".log";
	}

}
