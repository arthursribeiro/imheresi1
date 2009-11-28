package util;
import dados.BancoDeDadosTxt;


/**
 * Interface Mensagem
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 11 de setembro de 2009
 */

public abstract class Mensagem {

	protected String remetente;
	protected String destinatario;
	protected StringBuilder builder = new StringBuilder();
	protected BancoDeDadosTxt banco;
	protected String nomeDoArquivo;
		
	protected final String line = System.getProperty("line.separator");
	protected final String separador = System.getProperty("file.separator");
	
	/**
	 * Zera os atributos da Mensagem
	 */
	public void zerarAtributos() {
		this.remetente = "";
		this.destinatario = "";
		this.builder = new StringBuilder();
		this.banco = null;
		this.nomeDoArquivo = "";
	}
	
	/*
	 * Comportamento padrao para todas as mensagens (Template), e ao final a mensagem é
	 * guardada no banco de dados
	 */
	public void enviarMensagem(String loginRemetente, String destinatario, String mensagem, BancoDeDadosTxt banco) {
		this.zerarAtributos();
		
		this.banco = banco;
		this.iniciar(loginRemetente, destinatario, mensagem);
		this.agregarCabecalho();
		this.agregarConteudo();
		this.agregarRodape();
		this.guardarNoBanco();
	}
	
	/**
	 * metodo que inicia os atributos específicos das mensagens
	 */
	public abstract void iniciar(String loginRemetente, String destinatario, String mensagem);
	
	protected void agregarCabecalho() {
		this.builder.append("From: " + this.remetente + this.line);
		this.builder.append("to: " + this.destinatario + this.line);
	}
	
	/**
	 * metodo que agrega conteudos especificos de cada mensagem
	 */
	protected abstract void agregarConteudo();
	
	/**
	 * metodo que agrega o rodape
	 */
	protected void agregarRodape() {
		this.builder.append("***************************************************************************************************");
		this.builder.append("" + line);
	}
	
	/**
	 * metodo que guarda a mensagem toda no banco de dados
	 */
	protected void guardarNoBanco() {
		this.banco.guardarMensagem(this.builder.toString(), this.nomeDoArquivo);
	}
}
