package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;

public class Us4_Facade {

	private MainSystem mySystem;
	
	public Us4_Facade(){
		this.mySystem = new MainSystem();
	}
	
	public String login(String userName, String senha, String ip) throws Exception {
		return this.mySystem.logIn(userName, senha, ip);
	}

	public void logout(String id) throws Exception{
		mySystem.logOut(id);
	}
	
	public void confirmarCompartilhamento(String de, String com, int modo) throws Exception{
		this.mySystem.confirmSharing(de, com, modo);
	}
	
	public void recusarCompartilhamento(String de, String com) throws Exception{
		this.mySystem.refuseSharing(de, com);
	}
	
	public String enviarConvite(String de, String para) throws Exception{
		mySystem.sendInvitation(de, para);		
		return "Documento convite.txt enviado com sucesso.";
	}
	
	
	public void initMensageiro(String sistema){
		
	}
	
	public void setDiretorioGabaritos(String valor){
		
	}
	
	public String getAmigos(String id) throws Exception{
		return this.mySystem.getFriends(id);
	}
	
	public String cadastrarUsuario(String userName, String nome, String email,
			String senha, String phone, String ip) throws Exception {
		User user = mySystem.createUser(userName, senha, email, nome, phone);
		this.mySystem.logIn(userName, senha, ip);
		return user.getUserName();
	}
	
}
