package com.googlecode.imheresi1.project;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;

public class PublicInfo {

	private String name;
	private String login;
	private Position position;
	private String telephoneNumber;
	private String email;

	public PublicInfo() {

	}

	public Position getPosition() throws PositionException {
		if (this.position == null) {
			throw new PositionException("Nao foi possivel obter a localizacao.");
		}
		return this.position;
	}

	public String getName() {
		return this.name;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String log) {
		this.login = log;
	}

	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	public String getEMail() {
		return this.email;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public void setPosition(String ip) throws PositionException {
		this.position = new Position(ip);
	}

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

	public void setTelephoneNumber(String newTelephoneNumber) {
		this.telephoneNumber = newTelephoneNumber;
	}

	public void setEmail(String newEmail) {
		this.email = newEmail;
	}
}
