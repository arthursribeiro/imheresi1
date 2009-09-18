package com.googlecode.imheresi1.message;

/**
 * Class that implements an interface Message and handles the Invitation type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class Invitation implements Message {

	private String fromName;
	private String to;
	private String fromMail;

	/**
	 * Constructor
	 * 
	 * @param fromName
	 * @param fromMail
	 * @param to
	 */
	public Invitation(String fromName, String fromMail, String to) {
		this.fromName = fromName;
		this.to = to;
		this.fromMail = fromMail;
	}

	/**
	 * @return full message
	 */
	public String build() {
		StringBuilder sB = new StringBuilder();
		sB.append("From: iam@email.com");
		sB.append(System.getProperty("line.separator"));
		sB.append("to: " + this.to);
		sB.append(System.getProperty("line.separator"));
		sB.append("Subject: " + this.fromName
				+ " gostaria de compartilhar sua localização com você");
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB.append(this.fromName + " (" + this.fromMail + ") ");
		sB
				.append("gostaria de compartilhar sua localização com você usando o I'mHere. Você também pode fazer o mesmo com os seus amigos, para mais informações acesse http://iamhere.com.");
		sB.append(System.getProperty("line.separator"));
		sB.append("Para aceitar ou recusar, clique no link abaixo.");
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB.append("http://iamhere.com/compartilhar");
		sB.append(System.getProperty("line.separator"));
		sB.append(System.getProperty("line.separator"));
		sB
				.append("***************************************************************************************************");
		sB.append(System.getProperty("line.separator"));
		return sB.toString();
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return "files/outputs/convites.log";
	}

}
