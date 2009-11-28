package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import dados.BancoDeDadosTxt;


/**
 * Classe chat.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.3 13 de setembro de 2009
 */

public class Chat extends Mensagem {

	private ArrayList<String> listaMsg1;
	private ArrayList<String> listaMsg2;
	private String primeiro = "";
	private String usuario1 = "";
	private String usuario2 = "";
	private String ultimoAEnviar = "";
	
	/**
	 * Construtor que cria as duas listas de mensagens
	 */
	public Chat() {
		super();
		
		this.listaMsg1 = new ArrayList<String>();
		this.listaMsg2 = new ArrayList<String>();
	}
	
	/**
	 * Modifica o usuario1 do chat
	 * @param login
	 */
	public void setUsuario1(String login){
		this.usuario1 = login;
	}

	/**
	 * Modifica o usuario2 do chat
	 * @param login
	 */
	public void setUsuario2(String login){
		this.usuario2 = login;
	}
	
	/**
	 * retorna o usuario1
	 * @return
	 */
	public String getUsuario1(){
		return this.usuario1;
	}
	
	/**
	 * retorna o usuario2
	 * @return
	 */
	public String getUsuario2(){
		return this.usuario2;
	}
	
	/**
	 * Envia mensagem de chat.
	 */
	public void enviarMensagem(String remetente, String destinatario, String mensagem, BancoDeDadosTxt banco) {
		this.banco = banco;
		this.gerarMensagem(remetente, destinatario);
		this.guardarNoBanco();
	}
	
	/**
	 * Adiciona na lista certa de mensagens de chat.
	 * @param login
	 * @param msg
	 */
	public void enviePara(String login, String msg){
		if(login.equalsIgnoreCase(this.usuario1)){
			if(this.primeiro.equalsIgnoreCase("")){
				this.primeiro = this.usuario2;
			}
			if(this.ultimoAEnviar.equalsIgnoreCase(this.usuario2)){
				String mensagem = this.listaMsg1.get(this.listaMsg1.size()-1);
				mensagem += "  "  + msg;
				this.listaMsg1.add(this.listaMsg1.size()-1, mensagem);
			}else{
				this.listaMsg1.add(msg);	
			}
			this.ultimoAEnviar = this.usuario2;
		}else if (login.equalsIgnoreCase(this.usuario2)){
			if(this.primeiro.equalsIgnoreCase("")){
				this.primeiro = this.usuario1;
			}
			if(this.ultimoAEnviar.equalsIgnoreCase(this.usuario1)){
				String mensagem = this.listaMsg2.get(this.listaMsg2.size()-1);
				mensagem += "  "  + msg;
				this.listaMsg2.add(this.listaMsg2.size()-1, mensagem);
			}else{
				this.listaMsg2.add(msg);	
			}
			this.ultimoAEnviar = this.usuario1;
		}
	}
	
	/**
	 * retorna a ultima msg enviada para usuario com o login passado como parametro.
	 * @param login
	 * @return
	 */
	public String getMsg(String login){
		if(login.equalsIgnoreCase(this.usuario2)){
			return this.listaMsg1.get(this.listaMsg1.size()-1);
		}else if (login.equalsIgnoreCase(this.usuario1)){
			return this.listaMsg2.get(this.listaMsg2.size()-1);
		}
		return null;
	}
	
	/**
	 * Encerra o chat.
	 */
	public void encerreChat(){
        this.usuario1 = "";
        this.usuario2 = "";
        this.ultimoAEnviar = "";
        this.primeiro = "";
        for (int i = 0; i < this.listaMsg1.size(); i++) {
                this.listaMsg1.remove(i);
        }
        for (int i = 0; i < this.listaMsg2.size(); i++) {
                this.listaMsg2.remove(i);
        }
    }

	
	/**
	 * metodo para gerar o conteudo a ser guardado no banco de dados
	 * @param usuario1 usuario1 do chat
	 * @param usuario2 usuario2 do chat
	 */
	public void gerarMensagem(String usuario1, String usuario2) {
		String usuarioAntes = null;
		String usuarioDepois = null;
		if (usuario1.compareTo(usuario2) < 0) {
			usuarioAntes = usuario1;
			usuarioDepois = usuario2;
		}
		else {
			usuarioAntes = usuario2;
			usuarioDepois = usuario1;
		}
		this.nomeDoArquivo = usuarioAntes + "-" + usuarioDepois + ".log";
				
		String segundo = null;			
			
		ArrayList<String> primeiraLista = null;
		ArrayList<String> segundaLista = null;
		if (this.primeiro.equalsIgnoreCase(usuario1)) {
			this.primeiro = usuario1;
			segundo = usuario2;
			primeiraLista = this.listaMsg2;
			segundaLista = this.listaMsg1;
		}
		else {
			this.primeiro = usuario2;
			segundo = usuario1;
			primeiraLista = this.listaMsg1;
			segundaLista = this.listaMsg2;
		}

		for (int i = 0; i < primeiraLista.size() || i < segundaLista.size(); i++) {
			if (i < primeiraLista.size()) {
				this.builder.append(this.primeiro + ": " + primeiraLista.get(i) + this.line);
			}
			if (i < segundaLista.size())
				this.builder.append(segundo + ": " + segundaLista.get(i) + this.line);
		}
		
	}

	protected void agregarConteudo() {
	}

	public void iniciar(String loginRemetente, String destinatario,
			String mensagem) {
	}
}
