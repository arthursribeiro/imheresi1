package com.googlecode.imheresi1.acceptancetests;


import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;

public class Us1_Facade {

	private MainSystem mySystem;
	
	public void zerarSistema(){
		this.mySystem = new MainSystem();
	}
	
	//Encerra o sistema, gravando log e informacoes dos usuarios
	public void encerrarSistema(){
		
	}
	
	public void criarUsuario(String userName, String nome, String email, String senha, String telefone) throws Exception{
		this.mySystem.createUser(userName, senha, email, nome, telefone);

	}
	
	public String getUsuarioPorNome(String nome, int indice) throws Exception{
		return this.mySystem.getUserbyName(nome, indice).getUserName();
	}
	
	private User getUsuarioPorUserName(String userName) throws Exception{
		return this.mySystem.getUserByUserName(userName);
	}
	
	public String getAtributoUsuario(String userName, String atributo) throws Exception{
		User user = this.getUsuarioPorUserName(userName);	
		
		if(atributo.equalsIgnoreCase("nome")) return user.getName();
		if(atributo.equalsIgnoreCase("email")) return user.getMail();
		if(atributo.equalsIgnoreCase("userName")) return user.getUserName();
		if(atributo.equalsIgnoreCase("telefone")) return user.getPhone();
		
		return null;
	}
	
	public void atualizarUsuario(String userName, String atributo, String valor) throws Exception{
		if(atributo.equalsIgnoreCase("userName")) throw new Exception("Nao eh permitido alterar o username.");
		User user = this.getUsuarioPorUserName(userName);
		
		//Os updates utilizam os sets que ja lancam as excecoes.. refatoramento das exececoes!
		if(atributo.equalsIgnoreCase("nome")) user.setName(valor);
		if(atributo.equalsIgnoreCase("email")) user.setMail(valor);
		if(atributo.equalsIgnoreCase("telefone")) user.setPhone(valor);
		if(atributo.equalsIgnoreCase("senha")) user.setPassword(valor);
		
	}
	
	public void removerUsuario(String userName) throws Exception{
		this.mySystem.removeUser(userName);
	}
	
}
