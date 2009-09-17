package com.googlecode.imheresi1.acceptancetests;


import com.googlecode.imheresi1.project.MainSystem;


public class Us2_Facade {
	
	private MainSystem mySystem;
	
	public void zerarSistema(){
		this.mySystem = new MainSystem();
	}
	
	public void encerrarSistema(){
		
	}
	
	public void criarUsuario(String userName, String nome, String email, String senha, String telefone) throws Exception{
		this.mySystem.createUser(userName, senha, email, nome, telefone);
	}
	
	public String login(String userName, String Senha, String ip) throws Exception{
		return this.mySystem.logIn(userName, Senha, ip);
	}
	
	public boolean estaConectado(String userName) throws Exception{
		return this.mySystem.isConected(userName);
	}
	
	public void logout(String userName) throws Exception{
		this.mySystem.logOut(userName);
	}
	
	public String cadastrarUsuario(String userName, String nome, String email, String senha, 
			String telefone, String ip) throws Exception{
		this.mySystem.createUser(userName, senha, email, nome, telefone);
		this.mySystem.logIn(userName, senha, ip);
		return userName;
	}
}
