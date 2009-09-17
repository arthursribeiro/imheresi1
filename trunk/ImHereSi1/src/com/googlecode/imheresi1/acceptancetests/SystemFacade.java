package com.googlecode.imheresi1.acceptancetests;

import com.googlecode.imheresi1.localization.Position;
import com.googlecode.imheresi1.localization.PositionException;
import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.User;

public class SystemFacade {

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

	public String getLocalizacao(String userName) throws Exception,
			PositionException {
		User user = this.mySystem.getUserByUserName(userName);
		Position local = user.getPosition();
		if (local == null)
			throw new PositionException("Nao foi possivel obter a localizacao.");
		return user.getPosition().toString();
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
		
	public String enviarEmail(String de, String para, String assunto, String msg) throws Exception{	
		mySystem.sendMail(de, para, assunto, msg);
		return "Email enviado com sucesso.";
	}
	
	public String enviarSMS(String de, String para, String msg) throws Exception{	
		mySystem.sendSMS(de, para, msg);
		return "SMS enviado com sucesso.";
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
	
	public boolean possoVerLocalizacao(String usuario, String amigo) throws Exception{
		User user = this.mySystem.getUserByUserName(usuario);
		return user.isVisible(amigo);
	}
	
	public void setCompartilhamento(String usuario, String amigo, int modo) throws Exception{
		User user = this.mySystem.getUserByUserName(amigo);
		user.setSharingOption(usuario, modo);
	}
	
	public void removerAmigo(String usuario, String amigo) throws Exception {
		User user;
		try{
			user = this.mySystem.getUserByUserName(usuario);

		} catch (Exception ex){
			throw new Exception("Permissao negada.");
		}
		user.removeFriend(amigo);
	}
	
	public String getLocalizacaoAmigo(String userName, String amigo) throws PositionException, Exception {
		User user;
		try {
			user = this.mySystem.getUserByUserName(userName);
		} catch (Exception ex){
			throw new Exception("Permissao negada.");
		}
		Position pos = user.getFriendLocation(amigo);
		if (pos == null)
			return "Localizacao desconhecida.";
		return pos.toString();
	}
	
	
}

