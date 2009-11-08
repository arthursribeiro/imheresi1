package util;

/**
 * Classe SMS
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 11 de setembro de 2009
 */
public class SMS extends Mensagem{

	private String mensagem;

	
	/**
	 * inicia os atributos do SMS
	 */
	public void iniciar(String remetente, String destinatario, String mensagem) {
		Usuario userRemetente = this.banco.procureUsuarioLogin(remetente);

		this.remetente = userRemetente.getNome();
		this.destinatario = destinatario;
		this.mensagem = mensagem;
		this.nomeDoArquivo = this.banco.smsArq;
	}
	
	/**
	 * agrega os conteúdos específicos do SMS (Template)
	 */
	public void agregarConteudo() {
		this.builder.append(this.line);
		this.builder.append(this.mensagem + this.line);
		this.builder.append(this.line);
	}
}
