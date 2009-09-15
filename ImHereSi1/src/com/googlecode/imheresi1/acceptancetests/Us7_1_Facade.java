package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.project.MainSystem;

public class Us7_1_Facade {
	
	private MainSystem mySystem;
	
	public String login(String userName, String senha, String ip) throws Exception {
		return this.mySystem.logIn(userName, senha, ip);
	}
	
	public void initMensageiro(String sistema){
		//Nao sei o que isso faz...
	}
		
	public String enviarEmail(String de, String para, String assunto, String msg) throws Exception{	
		mySystem.sendMail(de, para, assunto, msg);
		return "Email enviado com sucesso.";
	}
	
	public void logout(String id) throws Exception{
		mySystem.logOut(id);
	}
	
}
