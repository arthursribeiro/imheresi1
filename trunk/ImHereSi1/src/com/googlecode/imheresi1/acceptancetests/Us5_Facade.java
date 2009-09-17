package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;

public class Us5_Facade {

	private MainSystem mySystem;
	
	public boolean possoVerLocalizacao(String usuario, String amigo) throws Exception{
		User user = this.mySystem.getUserByUserName(usuario);
		return user.isVisible(amigo);
	}
	
	public void setCompartilhamento(String usuario, String amigo, int modo) throws Exception{
		User user = this.mySystem.getUserByUserName(usuario);
		user.setSharingOption(amigo, modo);
	}
	
	public void removerAmigo(String usuario, String amigo) throws Exception {
		try{
			User user = this.mySystem.getUserByUserName(usuario);
			user.removeFriend(amigo);
		} catch (Exception ex){
			throw new Exception("Permissao negada.");
		}
	}
	
}
