package principal;
import java.util.ArrayList;

import util.LoginException;
import util.Usuario;
import util.UsuarioException;


/**
 * Essa classe eh a camada entre o sistema e os testes.
 * @author Delano, Joao Paulo, Izabela, Savyo
 *
 */

public class Facade {

	private Sistema sistema;

	public Facade() {
		sistema = Sistema.getInstancia();
	}
	
	/**
	 * Apaga todos os dados do banco de dados.
	 */

	public void zerarSistema() {
		sistema.zerarOSistema();
	}
	
	/**
	 * Cadastra o usuario, caso algum parametro seja null, ele transforma em uma String vazia.
	 * @param userName
	 * @param nome
	 * @param email
	 * @param senha
	 * @param telefone
	 */

	public void criarUsuario(String userName, String nome, String email,
			String senha, String telefone) {
		if(userName == null){
			userName = "";
		}
		if(nome == null){
			nome = "";
		}
		if(email == null){
			email = "";
		}
		if(senha == null){
			senha = "";
		}
		if(telefone == null){
			telefone = "";
		}
		sistema.cadastro(userName, nome, senha, email, telefone, "127.0.0.1");
	}
	
	/**
	 * Procura os usuarios que possuem o nome e retorna do indice.
	 * @param nome
	 * @param indice
	 * @return login do usuario
	 */

	public String getUsuarioPorNome(String nome, int indice) {
		ArrayList<Usuario> usuarios = sistema.procurePorNome(nome);
		if (usuarios.size() > indice - 1)
			return usuarios.get(indice - 1).getUsername();
		throw new UsuarioException("O usuario nao existe.");
	}
	
	/**
	 * Retorna o atributo solicitado do usuario.
	 * @param id
	 * @param atributo
	 * @return atributo do usuario.
	 */

	public String getAtributoUsuario(String id, String atributo) {
		Usuario usuario = sistema.procurePorLogin(id);
		if (atributo.equals("nome"))
			return usuario.getNome();
		else if (atributo.equals("email"))
			return usuario.getEmail();
		else if (atributo.equals("userName"))
			return usuario.getUsername();
		else if (atributo.equals("telefone"))
			return usuario.getTelefone();
		return "";
	}
	
	/**
	 * Atualiza algum atributo do usuario com login id.
	 * @param id
	 * @param atributo
	 * @param valor
	 */

	public void atualizarUsuario(String id, String atributo, String valor) {
		if(id == null){
			id = "";
		}
		if(atributo == null){
			atributo = "";
		}
		if(valor == null){
			valor = "";
		}
		sistema.atualizaDadosUsuario(id, atributo, valor);
	}
	
	/**
	 * Remove o usuario do sistema.
	 * @param id
	 */
	
	public void removerUsuario(String id) {
		sistema.remove(id);
	}
	
	/**
	 * Loga o usuario no sistema.
	 * @param userName
	 * @param senha
	 * @param ip
	 * @return userName
	 */

	public String login(String userName, String senha, String ip) {
		return sistema.fazerLogin(userName, senha, ip).getUsername();
	}
	
	/**
	 * Fazer o logout do sistema.
	 * @param id
	 */
	
	public void logout(String id){
		Usuario usuario;
		try{
			usuario = sistema.procurePorLogin(id);
		} catch(UsuarioException e){
			usuario = null;
		}
		sistema.logout(usuario);
	}
	
	/**
	 * Verifica se o usuario esta conectado.
	 * @param usuario
	 * @return True - Conectado, False - Nao Conectado.
	 */
	
	public boolean estaConectado(String usuario) {
		try{
			return sistema.procurePorLogin(usuario).getEstaConectado();
		} catch (UsuarioException e) {
			return false;
		}
	}
	
	/**
	 * Encerra o Sistema.
	 */
	
	public void encerrarSistema(){
		
	}

	/**
	 * Cadastra o usuario, caso algum parametro seja null, ele transforma em uma String vazia.
	 * @param userName
	 * @param nome
	 * @param email
	 * @param senha
	 * @param telefone
	 */
	
	public String cadastrarUsuario(String userName, String nome, String email,
			String senha, String telefone, String ip) {
		criarUsuario(userName, nome, email, senha, telefone);
		return login(userName, senha, ip);
	}
	
	/**
	 * Retorna o tipo de localizador que esta sendo usado.
	 * @return tipo de localizador.
	 */
	
	public String getLocalizadores(){
		return sistema.getLocalizadores();
	}
	
	/**
	 * Retorna a latitude e a longitude da sua localizacao.
	 * @param usuario
	 * @return latitude e longitude.
	 */
	
	public String obterLocalizacao(String usuario){
		Usuario cliente = sistema.procurePorLogin(usuario);
		sistema.obterLocalizacao(cliente);
		return "Lat: " + cliente.getLatitude() + ", Long: " + cliente.getLongitude();
	}
	
	/**
	 * Modifica a latitude e a longitude da localizacao do usuario.
	 * @param usuario
	 * @param latitude
	 * @param longitude
	 */
	
	public void setLocalizacao(String usuario, double latitude, double longitude){
		sistema.setLocalizacao(sistema.procurePorLogin(usuario), latitude, longitude);
	}
	
	/**
	 * Retorna a localizacao do usuario.
	 * @param usuario
	 * @return latitude e longitude do usuario.
	 */
	
	public String getLocalizacao(String usuario){
		Usuario cliente = sistema.procurePorLogin(usuario);
		return "Lat: " + cliente.getLatitude() + ", Long: " + cliente.getLongitude();
	}
	
	/**
	 * Inicia o tipo de sistema de mensagem.
	 * @param sistema
	 */
	
	public void initMensageiro(String sistema){
		this.sistema.initMensageiro(sistema);
	}
	
	/**
	 * Modifica o diretorio dos gabaritos
	 * @param valor
	 */
	
	public void setDiretorioGabaritos(String valor){
		sistema.setDiretorioGabaritos(valor);
	}
	
	/**
	 * Envia um convite de amizade.
	 * @param de
	 * @param para
	 * @return aviso se foi enviado ou nao.
	 */
	
	public String enviarConvite(String de, String para){
		try{
			sistema.enviarConvite(sistema.procurePorLogin(de), para);
		} catch(Exception e){
			System.out.println(para);
		}
		return "Documento convite.txt enviado com sucesso.";
	}
	
	/**
	 * Confirma o compartilhamento de localizacao no modo 1 (oculto) ou modo 2 (visivel).
	 * @param de
	 * @param com
	 * @param modo
	 */
	
	public void confirmarCompartilhamento(String de, String com, int modo){
		sistema.confirmarCompartilhamento(de, com, modo);
	}
	
	/**
	 * Recusa o compartilhamento de localizacao.
	 * @param de
	 * @param com
	 */
	
	public void recusarCompartilhamento(String de, String com){
		sistema.recusarCompartilhamento(de, com);
	}
	
	/**
	 * Retorna os amigos do usuario.
	 * @param usuario
	 * @return [nome do amigo1, nome do amigo2, ...]
	 */
	
	public String getAmigos(String usuario){
		try{
			return sistema.getAmigos(sistema.procurePorLogin(usuario));
		} catch(UsuarioException e){
			throw new LoginException("Permissao negada.");
		}
	}
	
	/**
	 * Verifica se o usuario pode obter a localizacao de amigo.
	 * @param usuario
	 * @param amigo
	 * @return true - caso possa, falso - caso nao possa.
	 */
	
	public boolean possoVerLocalizacao(String usuario, String amigo){
		return	sistema.possoVerLocalizacao(usuario, amigo);
	}
	
	/**
	 * Modifica o tipo de compartilhamento entre usuario e amigo. modo=1(oculto), modo=2(visivel).
	 * @param usuario
	 * @param amigo
	 * @param modo
	 */
	
	public void setCompartilhamento(String usuario, String amigo, int modo){
		try {
			sistema.setCompartilhamento(usuario, amigo, modo);
		} catch (Exception e) {}
	}
	
	/**
	 * Remove o amigo da lista de amigos do usuario. 
	 * @param usuario
	 * @param amigo
	 */
	
	public void removerAmigo(String usuario, String amigo){
		sistema.removerAmigo(usuario, amigo);
	}
	
	/**
	 * Retorna a localizacao do usuario. (se for visivel)
	 * @param usuario
	 * @param amigo
	 * @return latitude e longitude do amigo
	 */
	
	public String getLocalizacaoAmigo(String usuario, String amigo){
		return sistema.getLocalizacaoAmigo(usuario, amigo);
	}
	
	/**
	 * Envia um email.
	 * @param de
	 * @param para
	 * @param assunto
	 * @param msg
	 * @return o status do envio.
	 */
	
	public String enviarEmail(String de, String para, String assunto, String msg){
		return sistema.enviarEmail(de, sistema.procurePorLogin(para).getEmail(), assunto, msg);
	}
	
	/**
	 * Envia um sms.
	 * @param de
	 * @param para
	 * @param msg
	 * @return status do envio.
	 */
	
	public String enviarSMS(String de, String para, String msg){
		return sistema.enviarSMS(de, sistema.procurePorLogin(para).getTelefone(), msg);
	}
	
	/**
	 * Modifica a porta usada no chat.
	 * @param port
	 */
	
	
	public void setPortChat(int port){
		sistema.setPortChat(port);
	}
	
	/**
	 * Inicia um chat entre o usuario1 e usuario2.
	 * @param usuario1
	 * @param usuario2
	 */
	
	public void initChat(String usuario1, String usuario2){
		sistema.initChat(usuario1, usuario2);
	}
	
	/**
	 * Envia uma mensagem no chat.
	 * @param para
	 * @param msg
	 */
	
	public void enviarMensagem(String para, String msg){
		sistema.enviarMensagem(para, msg);
	}
	
	/**
	 * Recebe uma mensagem no chat.
	 * @param de
	 * @return
	 */
	
	public String receberMensagem(String de){
		return sistema.receberMensagem(de);
	}
	
	/**
	 * Encerra o chat entre usuario1 e usuario2.
	 * @param usuario1
	 * @param usuario2
	 */
	
	public void encerrarChat(String usuario1, String usuario2){
		sistema.encerrarChat(usuario1, usuario2);
	}
}
