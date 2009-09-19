package com.googlecode.imheresi1.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;

/**
 * Class that implements the User type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class User {

	private List<PublicInfo> friends;
	private List<String> visibleFriends;
	private String password;
	private String ip;
	private PublicInfo myPublicInfo;

	/**
	 * Constructor
	 * 
	 * @param userName
	 * @param password
	 */
	public User(String userName, String password) throws UserException {
		setPassword(password);
		this.myPublicInfo = new PublicInfo();
		setUserName(userName);
		this.friends = new ArrayList<PublicInfo>();
		this.visibleFriends = new ArrayList<String>();
		this.ip = "000.0.0.0";
	}

	/**
	 * @return public info
	 */
	public PublicInfo getPublicInfo() {
		return this.myPublicInfo;
	}

	/**
	 * 
	 * @param friend
	 * @param mode
	 */
	public void addFriend(PublicInfo friend, int mode) throws UserException {
		if (this.friends.contains(friend))
			throw new UserException("Usuario ja eh amigo.");
		
		this.friends.add(friend);
		
		if (mode == 2)
			this.visibleFriends.add(friend.getLogin());
	}

	/**
	 * 
	 * @param newPass
	 * @throws UserException
	 */
	public void updatePassword(String newPass) throws UserException {
		if (newPass == null || password.trim().equals("")
				|| newPass.length() < 6)
			throw new UserException("Senha deve ter no minimo 6 caracteres.");
		this.password = newPass;
	}

	/**
	 * 
	 * @param friendUserName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isVisible(String friendUserName) throws Exception {
		for (int i = 0; i < this.friends.size(); i++) {
			PublicInfo pInfo = this.friends.get(i);
			if (pInfo.getLogin().equals(friendUserName))
				return this.visibleFriends.contains(friendUserName);
		}
		throw new Exception("Usuario desconhecido.");
	}

	/**
	 * 
	 * @return friends name sorted
	 */
	public String getFriendsNames() {
		String[] names = new String[this.friends.size()];

		for (int i = 0; i < names.length; i++) {
			names[i] = this.friends.get(i).getName();
		}

		Arrays.sort(names);
		return Arrays.toString(names);
	}

	/**
	 * 
	 * @return collection of PublicInfo
	 */
	public List<PublicInfo> getFriendsPublicInfo() {
		return this.friends;
	}

	/**
	 * @param ip
	 */
	public void setIp(String ip) throws UserException {
		if (!this.validIp(ip))
			throw new UserException("IP invalido.");
		this.ip = ip;
	}

	/**
	 * 
	 * @param Ip
	 * @return boolean
	 */
	private boolean validIp(String Ip) {
		String expression = "^((0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})\\.){3}(0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})$";

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(Ip);

		return matcher.matches();
	}

	/**
	 * 
	 * @param email
	 * @throws UserException
	 */
	public void setMail(String email) throws UserException {
		if ((email == null) || (email.trim().equals("")))
			throw new UserException("E-mail eh um dado obrigatorio.");
		if (!this.validMail(email))
			throw new UserException("E-mail invalido.");
		this.myPublicInfo.setEmail(email);
	}

	/**
	 * 
	 * @param email
	 * @return boolean
	 */
	private boolean validMail(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	/**
	 * 
	 * @param password
	 * @throws UserException
	 */
	public void setPassword(String password) throws UserException {
		if ((password == null) || (password.trim().equals("")))
			throw new UserException("Senha eh um dado obrigatorio.");
		if (password.length() < 6)
			throw new UserException("Senha deve ter no minimo 6 caracteres.");
		this.password = password;
	}

	/**
	 * 
	 * @param name
	 * @throws UserException
	 */
	public void setName(String name) throws UserException {
		if ((name == null) || (name.trim().equals("")))
			throw new UserException("Nome eh um dado obrigatorio.");
		this.myPublicInfo.setName(name);
	}

	/*
	 * Nao Pode resetar o Username!!
	 */
	/**
	 * 
	 * @param userName
	 * @throws UserException
	 */
	private void setUserName(String userName) throws UserException {
		if ((userName == null) || (userName.trim().equals("")))
			throw new UserException("Username eh um dado obrigatorio.");
		this.myPublicInfo.setLogin(userName);
	}

	/**
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.myPublicInfo.setTelephoneNumber(phone);
	}

	/**
	 * 
	 * @throws PositionException
	 */
	public void setPosition() throws PositionException {
		this.myPublicInfo.setPosition(this.ip);
	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws PositionException
	 */
	public void setPositionManual(double latitude, double longitude)
			throws PositionException {
		this.myPublicInfo.setPositionManual(latitude, longitude);
	}

	/**
	 * 
	 * @return Position
	 * @throws PositionException
	 */
	public Position getPosition() throws PositionException {
		return this.myPublicInfo.getPosition();
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return this.myPublicInfo.getName();
	}

	/**
	 * 
	 * @return email
	 */
	public String getMail() {
		return this.myPublicInfo.getEMail();
	}

	/**
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return this.myPublicInfo.getTelephoneNumber();
	}

	/**
	 * 
	 * @return username
	 */
	public String getUserName() {
		return this.myPublicInfo.getLogin();
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	private boolean isMyFriend(String username) {
		for (PublicInfo pInfo : this.friends) {
			if (pInfo.getLogin().equals(username))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param friend
	 * @param mode
	 * @throws Exception
	 */
	public void setSharingOption(String friend, int mode) throws Exception {
		if (!isMyFriend(friend))
			throw new Exception("Usuario desconhecido.");
		if (mode == 2) {
			if (!this.visibleFriends.contains(friend))
				this.visibleFriends.add(friend);
		} else {
			if (this.visibleFriends.contains(friend))
				this.visibleFriends.remove(friend);
		}
	}

	/**
	 * 
	 * @param friend
	 * @throws UserException
	 */
	public void removeFriend(String friend) throws UserException {
		for (PublicInfo pInfo : this.friends) {
			if (pInfo.getLogin().equals(friend)) {
				this.friends.remove(pInfo);
				if (this.visibleFriends.contains(pInfo.getLogin()))
					this.visibleFriends.remove(pInfo.getLogin());
				return;
			}
		}
		throw new UserException("Usuario desconhecido.");
	}

	/**
	 * 
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public Position getFriendLocation(String friend) throws Exception {
		if (!isMyFriend(friend))
			throw new Exception("Usuario desconhecido.");
		for (PublicInfo pInfo : this.friends) {
			if (pInfo.getLogin().equals(friend)
					&& this.visibleFriends.contains(pInfo.getLogin())) {
				return pInfo.getPosition();
			}
		}
		return null;
	}

	public boolean willChangeIp(String ip2) {
		return !this.ip.equals(ip2);
	}

	public PublicInfo getAFriendPublicInfo(String userName) {
		for(PublicInfo pInfo : this.friends){
			if(pInfo.getLogin().equals(userName)){
				return pInfo;
			}
		}
		return null;
	}

	public String toStringFriends() {
		String separator = System.getProperty("line.separator"); 
		String saida = "==================================================================" + separator
					   + "Username                      Nome                            " + separator
					   + "================================================================="  + separator;
		
		if(this.friends.size() == 0) return "";
		
		for(int i = 0; i < this.friends.size(); i++){
			saida += this.friends.get(i).getLogin() + "      " + this.friends.get(i).getName() + separator;
		}
		return saida;
	}
}
