package com.googlecode.imheresi1.message;

/**
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 *
 */
public class SMS implements Message {

	private String from;
	private String to;
	private String msg;
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param msg
	 */
	public SMS(String from, String to, String msg){
		this.from = from;
		this.to = to;
		this.msg = msg;
	}
	
	/**
	 * @return full message
	 */
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

	/**
	 * @return path
	 */
	public String getPath() {
		return "files/outputs/sms.log";
	}

}
