package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;
import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;


public class Us3_Facade {

	private MainSystem mySystem;

	public void zerarSistema() {
		this.mySystem = new MainSystem();
	}

	// Encerra o sistema, gravando log e informacoes dos usuarios
	public void encerrarSistema() {
		// oi, eu existo
	}

	public String cadastrarUsuario(String userName, String nome, String email, String senha, 
			String telefone, String ip) throws Exception{
		this.mySystem.createUser(userName, senha, email, nome, telefone);
		this.mySystem.logIn(userName, senha, ip);
		return userName;
	}

	public String getLocalizadores() {
		return "[GeoIP, Manual]";
	}

	public String obterLocalizacao(String userName) throws Exception,
			PositionException {
		User user = this.mySystem.getUserByUserName(userName);
		Position local = user.getPosition();
		if (local == null)
			throw new PositionException("Nao foi possivel obter a localizacao.");
		return local.toString();
	}

	public void setLocalizacao(String userName, double latitude,
			double longitude) throws Exception, PositionException {
		User user = this.mySystem.getUserByUserName(userName);
		user.setPositionManual(latitude, longitude);
	}

	public void logout(String userName) throws Exception{
		this.mySystem.logOut(userName);
	}
	
	public String getLocalizacao(String userName) throws Exception,
			PositionException {
		User user = this.mySystem.getUserByUserName(userName);
		Position local = user.getPosition();
		if (local == null)
			throw new PositionException("Nao foi possivel obter a localizacao.");
		return user.getPosition().toString();
	}

}
