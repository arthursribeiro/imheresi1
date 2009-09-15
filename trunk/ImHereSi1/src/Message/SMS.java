package Message;

public class SMS implements Message {

	private String from;
	private String to;
	private String msg;
	
	public SMS(String from, String to, String msg){
		this.from = from;
		this.to = to;
		this.msg = msg;
	}
	
	public String build() {
		
		StringBuilder sB = new StringBuilder();
		
		sB.append("From: "+this.from);
		sB.append(System.getProperty("line.separator"));
		sB.append("to: "+this.to);
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB.append(this.msg);
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB.append("***************************************************************************************************");
		sB.append(System.getProperty("line.separator"));
		
		return sB.toString();
	}

	public String getPath() {
		return "files/outputs/sms.log";
	}

}
