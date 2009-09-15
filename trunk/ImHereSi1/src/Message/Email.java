package Message;

public class Email implements Message{

	private String from;
	private String to;
	private String subject;
	private String msg;
	
	public Email(String from, String to, String subject, String msg){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.msg = msg;
	}
	
	public String getPath(){
		return "files/outputs/emails.log";
	}
	
	public String build(){
		
		StringBuilder sB = new StringBuilder();
		
		sB.append("From: "+this.from);
		sB.append(System.getProperty("line.separator"));
		sB.append("to: "+this.to);
		sB.append(System.getProperty("line.separator"));
		sB.append("Subject: "+this.subject);
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB.append(this.msg);
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB.append("***************************************************************************************************");
		sB.append(System.getProperty("line.separator"));
		return sB.toString();
	}
	
}
