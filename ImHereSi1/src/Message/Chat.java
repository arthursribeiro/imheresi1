package Message;

public class Chat implements Message {

	private String user1;
	private String user2;
	
	private StringBuilder sB;
	
	public Chat(String u1, String u2) {
		this.user1 = u1;
		this.user2 = u2;
		sB = new StringBuilder();
	}

	public void addMsg(String receiver, String msg){
		if(receiver.equals(user1)) sB.append(user2);
		else sB.append(user1);
		sB.append(": "+msg);
		sB.append(System.getProperty("line.separator"));
	}
	

	public String build() {
		return sB.toString();
	}


	public String getPath() {
		if(user1.compareToIgnoreCase(user2) > 0) return  "files/chats/"+user2+"-"+user1+".log";
		return "files/chats/"+user1+"-"+user2+".log";
	}

}
