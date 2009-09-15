package com.googlecode.imheresi1.project;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;



public class User{

	private Collection<PublicInfo> friends;
	private List<String> visibleFriends;
	private String password;
	private String ip;
	private PublicInfo myPublicInfo;
	
	//Usar sets para setar qualquer tipo de atributo devido aos tratamentos de erros!
	public User(String userName, String password) throws Exception {
		setPassword(password);
		this.myPublicInfo = new PublicInfo();
		setUserName(userName);
	}

	public PublicInfo getPublicInfo(){
		return this.myPublicInfo;
	}
		
	public void addFriend(PublicInfo friend,int mode){
		this.friends.add(friend);
		if(mode == 2) this.visibleFriends.add(friend.getLogin());
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}

	public String getFriendsUserNames(){
		StringBuffer sB = new StringBuffer();
		sB.append('[');
		Iterator<PublicInfo> it = this.friends.iterator();
		while(it.hasNext()){
			PublicInfo pInfo = it.next();
			sB.append(pInfo.getLogin());
			if(it.hasNext()) sB.append(", ");
		}
		sB.append(']');
		return sB.toString();
	}
	
	
	//Coloquei os tratamentos de erros nos sets aqui pra poder dar uma refatorada! Lembrar de sempre usar os sets pra colocafr alguma coisa
	//inclusive no construtor!
	public void setMail(String email) throws Exception{
		if((email == null) || (email.trim().equals(""))) throw new Exception("E-mail eh um dado obrigatorio.");
		if(!this.validMail(email)) throw new Exception("E-mail invalido.");
		this.myPublicInfo.setEmail(email);
	}

	private boolean validMail(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	       
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
	}	

	public void setPassword(String password) throws Exception{
		if((password == null) || (password.trim().equals(""))) throw new Exception("Senha eh um dado obrigatorio.");
		if(password.length() < 6) throw new Exception("Senha deve ter no minimo 6 caracteres.");
		this.password = password;
	}
	
	public void setName(String name) throws Exception{
		if((name == null) || (name.trim().equals(""))) throw new Exception("Nome eh um dado obrigatorio.");
		this.myPublicInfo.setName(name);
	}
	
	/*
	 * Nao Pode resetar o Username!! 
	 */
	private void setUserName(String userName) throws Exception{
		if((userName == null) || (userName.trim().equals(""))) throw new Exception("Username eh um dado obrigatorio.");
		this.myPublicInfo.setLogin(userName);
	}

	public void setPhone(String phone) {
		this.myPublicInfo.setTelephoneNumber(phone);
	}
	
	public void setPosition() throws PositionException {
		this.myPublicInfo.setPosition(this.ip);
	}
	
	public void setPositionManual(double latitude, double longitude) throws PositionException {
		this.myPublicInfo.setPositionManual(latitude, longitude);
	}
	
	public Position getPosition() throws PositionException {
		return this.myPublicInfo.getPosition();
	}
	
		
	public String getPassword(){
		return this.password;
	}
	
	public String getName() {
		return this.myPublicInfo.getName();
	}

	public String getMail() {
		return this.myPublicInfo.getEMail();
	}
	

	public String getPhone() {
		return this.myPublicInfo.getTelephoneNumber();
	}

	public String getUserName() {
		return this.myPublicInfo.getLogin();
	}
	
}
