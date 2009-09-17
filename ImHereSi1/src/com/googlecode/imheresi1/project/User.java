package com.googlecode.imheresi1.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;



public class User{

	private List<PublicInfo> friends;
	private List<String> visibleFriends;
	private String password;
	private String ip;
	private PublicInfo myPublicInfo;
	
	//Usar sets para setar qualquer tipo de atributo devido aos tratamentos de erros!
	public User(String userName, String password) throws Exception {
		setPassword(password);
		this.myPublicInfo = new PublicInfo();
		setUserName(userName);
		this.friends = new ArrayList<PublicInfo>();
		this.visibleFriends = new ArrayList<String>();
	}

	public PublicInfo getPublicInfo(){
		return this.myPublicInfo;
	}
		
	public void addFriend(PublicInfo friend,int mode) throws Exception{
		if(this.friends.contains(friend)) throw new Exception("Usuario ja eh amigo.");
		this.friends.add(friend);
		if(mode == 2) this.visibleFriends.add(friend.getLogin());
	}
	
	public boolean isVisible(String friendUserName) throws Exception{
		for(int i = 0; i < this.friends.size(); i++){
			PublicInfo pInfo = this.friends.get(i);
			if(pInfo.getLogin().equals(friendUserName)) return this.visibleFriends.contains(friendUserName);
		}
		throw new Exception("Usuario desconhecido.");
	}
	
	public String getFriendsNames(){
		String[] names = new String[this.friends.size()];
		
		for(int i = 0; i < names.length; i++){
			names[i] = this.friends.get(i).getName();
		}
		
		Arrays.sort(names);
		return Arrays.toString(names);
	}
	
	public List<PublicInfo> getFriendsPublicInfo(){
		return this.friends;
	}
	
	
	//Coloquei os tratamentos de erros nos sets aqui pra poder dar uma refatorada! Lembrar de sempre usar os sets pra colocafr alguma coisa
	//inclusive no construtor!
	public void setIp(String ip) throws Exception{
		if(!this.validIp(ip)) throw new Exception("IP invalido.");
		this.ip = ip;
	}
	
	private boolean validIp(String Ip){
		String expression = "^((0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})\\.){3}(0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})$";
	       
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Ip);
        
        return matcher.matches();
	}
	
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

	private boolean isMyFriend(String username){
		for(PublicInfo pInfo : this.friends){
			if(pInfo.getLogin().equals(username)) return true;
		}
		return false;
	}
	
	public void setSharingOption(String friend, int mode) throws Exception {
		if(!isMyFriend(friend)) throw new Exception("Usuario desconhecido.");
		if(mode == 2){
			if(!this.visibleFriends.contains(friend)) this.visibleFriends.add(friend);
		} else {
			if(this.visibleFriends.contains(friend)) this.visibleFriends.remove(friend);
		}
	}

	public void removeFriend(String friend) throws Exception  {
		for(PublicInfo pInfo : this.friends){
			if(pInfo.getLogin().equals(friend)) {
				this.friends.remove(pInfo);
				if(this.visibleFriends.contains(pInfo.getLogin())) this.visibleFriends.remove(pInfo.getLogin());
				return;
			}
		}
		throw new Exception("Usuario desconhecido.");
	}

	public Position getFriendLocation(String friend) throws Exception {
		if(!isMyFriend(friend)) throw new Exception("Usuario desconhecido.");
		for(PublicInfo pInfo : this.friends){
			if(pInfo.getLogin().equals(friend) && this.visibleFriends.contains(pInfo.getLogin())) {
				return pInfo.getPosition();
			}
		}
		return null;
	}
	
}
