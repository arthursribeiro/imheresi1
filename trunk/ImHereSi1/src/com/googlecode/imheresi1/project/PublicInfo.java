package com.googlecode.imheresi1.project;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;

/**
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 *
 */
public class PublicInfo {

	private String name;
	private String login;
	private Position position;
	private String telephoneNumber;
	private String email;

	/**
	 * Constructor
	 */
	public PublicInfo() {

	}

	/**
	 * @return Position - The position of the user
	 * @throws PositionException
	 */
	public Position getPosition() throws PositionException {
		if (this.position == null) {
			throw new PositionException("Nao foi possivel obter a localizacao.");
		}
		return this.position;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * 
	 * @param log
	 */
	public void setLogin(String log) {
		this.login = log;
	}

	/**
	 * 
	 * @return phone number
	 */
	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	/**
	 * 
	 * @return email
	 */
	public String getEMail() {
		return this.email;
	}

	/**
	 * 
	 * @param newName
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * 
	 * @param ip
	 * @throws PositionException
	 */
	public void setPosition(String ip) throws PositionException {
		this.position = new Position(ip);
	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws PositionException
	 */
	public void setPositionManual(double latitude, double longitude)
			throws PositionException {
		if (this.position == null) {
			this.position = new Position(latitude, longitude);
		} else { // Se eu ja tenho uma localizacao valida
			// q foi criada a partir de um ip, eu posso
			// setar manualmente uma nova lat e longi? :B
			this.position.setPosition(latitude, longitude);
		}
	}

	/**
	 * 
	 * @param newTelephoneNumber
	 */
	public void setTelephoneNumber(String newTelephoneNumber) {
		this.telephoneNumber = newTelephoneNumber;
	}

	/**
	 * 
	 * @param newEmail
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}
}
