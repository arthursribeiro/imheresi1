package Project;

import Localization.Position;

public class PublicInfo {

	private String name;
	private String login;
	private Position position;
	private boolean visible;
	private String telephoneNumber;
	private String email;
	
	public PublicInfo(boolean visible){
		this.visible = visible;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getLogin(){
		return this.login;
	}
	
	public void setLogin(String log){
		this.login = log;
	}
	
	public String getTelephoneNumber(){
		return this.telephoneNumber;
	}
	
	public String getEMail(){
		return this.email;
	}
		
	public void setName(String newName){
		this.name = newName;
	}
	
	public void setTelephoneNumber(String newTelephoneNumber){
		this.telephoneNumber = newTelephoneNumber;
	}

	public void setEmail(String newEmail){
		this.email = newEmail;
	}
}
