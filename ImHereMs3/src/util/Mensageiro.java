package util;
import dados.BancoDeDadosTxt;


/**
 * Classe mensageiro. Eh o responsavel por enviar os diferentes tipos de mensagens.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.5 13 de setembro de 2009
 */

public class Mensageiro {

	private BancoDeDadosTxt banco;
	
	/**
	 * Construtor.
	 * @param banco
	 */
	public Mensageiro(BancoDeDadosTxt banco){
		this.banco = banco;
	}
	
	//O login será passado como parametro!!
	/**
	 * Envia mensagens de qualquer tipo.
	 */
	public void enviarMensagem(Mensagem tipoMensagem, String loginRemetente, String loginDestinatario, String mensagem){
		tipoMensagem.enviarMensagem(loginRemetente, loginDestinatario, mensagem, this.banco);
	}
	
	/**
	 * envia mensagens do tipo Email, setando o assunto do Email e invocando o metodo para
	 * envio de mensagens de qualquer tipo.
	 */
	public void enviarMensagem(Mensagem tipoMensagem, String loginRemetente, String loginDestinatario, String mensagem, String assunto){
		((Email) tipoMensagem).setAssunto(assunto);
		this.enviarMensagem(tipoMensagem, loginRemetente, loginDestinatario, mensagem);
	}
}
