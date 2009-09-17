package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.project.MainSystem;


public class Us7_3_Facade {
	
	private MainSystem mySystem;
	
	public String login(String userName, String senha, String ip) throws Exception {
		return this.mySystem.logIn(userName, senha, ip);
	}
	
	public void zerarSistema(){
		this.mySystem = new MainSystem();
	}
	
	public void initMensageiro(String sistema){
		//Nao sei o que isso faz...
	}
	
	public void setPortChat(String port){
		//Nao sei o que isso faz...
	}
	
	public void initChat(String u1, String u2){
		mySystem.initChat(u1, u2);
	}
	
	public String receberMensagem(String userName){
		return "";
	}
	
	public void enviarMensagem(String receiver, String msg) throws Exception{
		mySystem.sendChat(receiver, msg);
	}
	
	public void encerrarChat(String us1, String us2) throws Exception{
		mySystem.endChat();
	}
	
}
