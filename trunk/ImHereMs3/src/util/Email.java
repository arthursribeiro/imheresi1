package util;


/**
 * Classe email.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 11 de setembro de 2009
 */

public class Email extends Mensagem{

	protected String assunto;
	protected String mensagem;
	
	/**
	 * Inicia os atributos do Email
	 */
	public void iniciar(String remetente, String destinatario, String mensagem) {
		Usuario userRemetente = this.banco.procureUsuarioLogin(remetente);

		this.remetente = userRemetente.getEmail();
		this.destinatario = destinatario;
		this.mensagem = mensagem;
		this.nomeDoArquivo = this.banco.emailArq;
	}
	
	/**
	 * metodo para agregarConteudo especifico do email, para escrita no banco de dados (Template)
	 */
	public void agregarConteudo() {
		this.builder.append("Subject: " + this.assunto + this.line);
		this.builder.append("" + line);
		this.builder.append(this.mensagem + line);
		this.builder.append("" + line);
	}
	
	/**
	 * Modifica o assunto do email.
	 * @param assunto
	 */
	public void setAssunto(String assunto){
		this.assunto = assunto;
	}

}
