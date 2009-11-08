package util;
/**
 * Classe Amigo.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 11 de setembro de 2009
 */

public class Amigo {

	private String usuario;
	private int modo;
	
	/**
	 * Modo 1: Oculto => o usuario visualiza a localizacao, mas nao disponibiliza a sua.
	 * Modo 2: Ambos visualizam a localização um do outro.
	 * @param usuario
	 */
	public Amigo(String usuario, int modo){
		this.usuario = usuario;
		this.modo = modo;
	}
	
	/**
	 * Retorna o usuario correspondente ao objeto amigo.
	 * @return
	 */
	public String getUsuario(){
		return this.usuario;
	}
	
	/**
	 * Retorna o modo de compartilhamento.
	 * @return
	 */
	public int getModo(){
		return this.modo;
	}
	
	/**
	 * Modifica o modo.
	 */
	public void setModo(int modo){
		this.modo = modo;
	}
	
	/**
	 * retorna o usuario do amigo
	 */
	public String toString() {
		return usuario;
	}
}
