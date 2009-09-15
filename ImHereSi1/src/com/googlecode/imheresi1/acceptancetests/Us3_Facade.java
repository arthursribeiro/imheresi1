package com.googlecode.imheresi1.acceptancetests;

import java.util.ArrayList;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;
import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;


public class Us3_Facade {

	private MainSystem mySystem;
	private ArrayList<User> createdUsers;

	public void zerarSistema() {
		this.mySystem = new MainSystem();
		this.createdUsers = new ArrayList<User>();
	}

	// Encerra o sistema, gravando log e informacoes dos usuarios
	public void encerrarSistema() {
		// oi, eu existo
	}

	public String cadastrarUsuario(String userName, String nome, String email,
			String senha, String phone, String ip) throws Exception {
		User user = mySystem.createUser(userName, senha, email, nome, phone);
		this.createdUsers.add(user);
		return user.getUserName();
	}

	public String getLocalizadores() {
		return "[GeoIP, Manual]";
	}

	public String obterLocalizacao(String userName) throws Exception,
			PositionException {
		User user = getUsuarioPorUserName(userName);
		Position local = user.getPosition();
		if (local == null)
			throw new PositionException("Nao foi possivel obter a localizacao.");
		return local.toString();
	}

	public void setLocalizacao(String userName, double latitude,
			double longitude) throws Exception, PositionException {
		User user = getUsuarioPorUserName(userName);
		user.setPositionManual(latitude, longitude);
	}

	public String getLocalizacao(String userName) throws Exception,
			PositionException {
		User user = getUsuarioPorUserName(userName);
		Position local = user.getPosition();
		if (local == null)
			throw new PositionException("Nao foi possivel obter a localizacao.");
		return user.getPosition().toString();
	}

	private User getUsuarioPorUserName(String userName) throws Exception {
		for (User usuario : this.createdUsers) {
			if (usuario.getUserName().equals(userName))
				return usuario;
		}

		throw new Exception("O usuario nao existe.");
	}

}
