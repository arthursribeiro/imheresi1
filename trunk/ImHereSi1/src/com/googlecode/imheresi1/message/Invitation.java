package com.googlecode.imheresi1.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	private String directory;

	/**
	 * Constructor
	 * 
	 * @param fromName
	 * @param fromMail
	 * @param to
	 */
	public Invitation(String fromName, String fromMail, String to, String dir) {
		this.fromName = fromName;
		this.to = to;
		this.fromMail = fromMail;
		this.directory = dir;
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
		
		String content = getInviteText();
		
		content = content.replace("<nomeUsuario>", this.fromName);
		content = content.replace("<emailUsuario>", this.fromMail);
		
		sB.append(content);
		sB.append(System.getProperty("line.separator"));
		sB
		.append("***************************************************************************************************");
		sB.append(System.getProperty("line.separator"));

		return sB.toString();
	}

	private String getInviteText(){
		StringBuffer buffer = new StringBuffer();
		try {
			Scanner in = new Scanner(new File(this.directory+"/convite.txt"));
			while(in.hasNext()){
				buffer.append(in.nextLine());
				buffer.append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * @return path
	 */
	public String getPath() {
		return "files/outputs/convites.log";
	}

}
