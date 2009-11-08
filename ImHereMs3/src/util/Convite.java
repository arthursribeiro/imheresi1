package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe convite.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 11 de setembro de 2009
 */

public class Convite extends Email{

	/**
	 * metodo que seta os atributos específicos do convite, que são diferentes do Email
	 */
	public void iniciar(String remetente, String destinatario, String mensagem) {
		Usuario userRemetente = this.banco.procureUsuarioLogin(remetente);
		this.assunto = userRemetente.getNome() + " gostaria de compartilhar sua localização com você";
		
		Usuario usuarioDestino = this.banco.procureUsuarioEmail(destinatario);
		if(usuarioDestino != null){
			usuarioDestino.adicionarConvites(userRemetente.getUsername());
			this.banco.atualizarUsuario(usuarioDestino);
		}
		
		this.mensagem = "";
		try {
			File gabarito = new File(this.banco.diretorioGabaritos + this.separador + "convite.txt");
			if (gabarito.exists()) {
				Scanner scanner = new Scanner(gabarito);
				String linha = "";
				while (scanner.hasNextLine()) {
					this.mensagem += linha + scanner.nextLine();
					linha = this.line;
				}
				this.mensagem = this.mensagem.replace("<nomeUsuario>", userRemetente.getNome());
				this.mensagem = this.mensagem.replace("<emailUsuario>", userRemetente.getEmail());
				scanner.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.remetente = "iam@email.com";
		this.destinatario = destinatario;
		this.nomeDoArquivo = this.banco.conviteArq;
	}
	
}
