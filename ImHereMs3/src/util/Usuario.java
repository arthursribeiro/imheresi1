package util;
import java.util.ArrayList;
import java.util.Iterator;



/**
 * Classe Usuario.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 07 de setembro de 2009
 */

public class Usuario {

	private String username;
	private String nome;
	private String senha;
	private String email;
	private String telefone;
	private String ip;
	private boolean estahConectado;
	private String latitude;
	private String longitude;
	private ArrayList<String> convitesPendentes;
	private VetorAmigo amigos;
	
	/**
	 * Construtor da classe Usuario
	 * @param username
	 * @param nome
	 * @param senha
	 * @param email
	 * @param telefone
	 */
	public Usuario(String username, String nome, String senha, String email, String telefone, String ip){
		this.username = username;
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.telefone = telefone;
		this.estahConectado = false;
		this.ip = ip;
		this.amigos = new VetorAmigo();
		this.convitesPendentes = new ArrayList<String>();
	}

	public String getIP(){
		return ip;
	}
	
	public void setIP(String novoIP){
		this.ip = novoIP;
	}
	
	public void setEstaConectado(boolean valor){
		this.estahConectado = valor;
	}
	
	public boolean getEstaConectado(){
		return estahConectado;
	}
	
	public void deletarConvite(String remetente){
		
		int cursor = 0;
		Iterator<String> iterConvitesPendentes = convitesPendentes.iterator();
		while(iterConvitesPendentes.hasNext()){
			if(convitesPendentes.get(cursor).equalsIgnoreCase(remetente)){
				convitesPendentes.remove(cursor);
				return;
			}
			cursor++; iterConvitesPendentes.next();
		}
	}
	
	public void adicionarConvites(String login){
		convitesPendentes.add(login);
	}
	
	public boolean temConvites(){
		return convitesPendentes.size() != 0;
	}
	
	public Iterator<String> getConvites(){
		return convitesPendentes.iterator();
	}
	
	/**
	 * Recupera o username
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Atualiza o username
	 * @param novoUsername
	 */
	public void setUsername(String novoUsername){
		this.username = novoUsername;
	}
	
	/**
	 * Recupera o nome
	 * @return
	 */
	public String getNome(){
		return nome;
	}
	
	/**
	 * Atualiza o nome
	 * @param novoNome
	 */
	public void setNome(String novoNome){
		this.nome = novoNome;
	}
	
	/**
	 * Recupera a senha
	 * @return
	 */
	public String getSenha(){
		return senha;
	}
	
	/**
	 * Atualiza a senha
	 * @param novaSenha
	 */
	public void setSenha(String novaSenha){
		this.senha = novaSenha;
	}
	
	/**
	 * Recupera o email
	 * @return
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Atualiza o email
	 * @param novoEmail
	 */
	public void setEmail(String novoEmail){
		this.email = novoEmail;
	}
	
	/**
	 * Recupera o telefone
	 * @return
	 */
	public String getTelefone(){
		return telefone;
	}
	
	/**
	 * Atualiza o telefone
	 * @param novoTelefone
	 */
	public void setTelefone(String novoTelefone){
		this.telefone = novoTelefone;
	}
	
	/**
	 * Compara se o username passado por parametro eh igual ao username do Usuario
	 * @param username2
	 * @return boolean
	 */
	public Boolean compareUsername(String username2){
		if(username2.equalsIgnoreCase(this.username)){
			return true;
		}
		return false;
	}
	
	/**
	 * Compara se o email passado por parametro eh igual ao email do Usuario
	 * @param email2
	 * @return
	 */
	public Boolean compareEmail(String email2){
		if(email2.equalsIgnoreCase(this.email)){
			return true;
		}
		return false;
	}

	/**
	 * Compara se a senha digitada estah correta
	 * @param senhaDigitada
	 * @return
	 */
	public boolean compareSenha(String senhaDigitada) {
		if(this.senha.equals(senhaDigitada)){
			return true;
		}
		return false;
	}
	
	/**
	 * Imprime as informacoes do usuario
	 */
	public String toString(){
		return ("Login: " + username) + "\n" + ("Nome: " + nome) + "\n" + ("Email: " + email) + "\n" + ("Telefone: " + telefone);
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}
	
	public String getLongitude(){
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void adicionarAmigo(Usuario usuario, int modo) {
		Amigo amigo = new Amigo(usuario.getUsername(), modo);
		this.amigos.addAmigo(amigo);
	}
	
	public void removerAmigo(String login) {
		
		Iterator<Amigo> iterAmigos = amigos.iterator();
		Amigo a;
		while(iterAmigos.hasNext()){
			a = iterAmigos.next();
			if(a.getUsuario().equalsIgnoreCase(login)){
				this.amigos.removeAmigo(a);
				return;
			}
		}
	}
	
	public boolean ehAmigoDe(String login) {
		
		Iterator<Amigo> iterAmigos = amigos.iterator();
		Amigo a;
		while(iterAmigos.hasNext()){
			a = iterAmigos.next();
			if(a.getUsuario().equalsIgnoreCase(login)){
				return true;
			} 
		}
		return false;
	}
	
	public VetorAmigo getListaAmigos() {
		return this.amigos;
	}
	
	public void modificarCompartilhamento(String login, int modo) {
		
		Iterator<Amigo> iterAmigos = amigos.iterator();
		Amigo a;
		while(iterAmigos.hasNext()){
			a = iterAmigos.next();
			if (a.getUsuario().equalsIgnoreCase(login)) {
				a.setModo(modo);
				return;
			}
		}
	}
}
