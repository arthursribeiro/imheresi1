package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;
import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;

public class Us6_Facade {

	private MainSystem mySystem;

	public void setLocalizacao(String userName, double latitude,
			double longitude) throws Exception, PositionException {
		User user = this.mySystem.getUserByUserName(userName);
		user.setPositionManual(latitude, longitude);
	}

	public void zerarSistema() {
		this.mySystem = new MainSystem();
	}

	public String getLocalizacaoAmigo(String userName, String amigo) throws PositionException, Exception {
		User user = this.mySystem.getUserByUserName(userName);
		Position pos = user.getFriendLocation(amigo);
		if (pos == null)
			throw new PositionException("Localizacao desconhecida.");
		return pos.toString();
	}
}
