package util;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Uma colecao de Amigos
 * @author Savyo
 *
 */

public class VetorAmigo {

	private ArrayList<Amigo> numbers = new ArrayList<Amigo>();
	
	/**
	 * Adiciona um Amigo a colecao.
	 * @param n
	 */
	
	public void addAmigo(Amigo n){
		this.numbers.add(n);
	}
	
	/**
	 * Retorna o amigo de indice index.
	 * @param index
	 * @return Amigo
	 */
	
	public Amigo getAmigo(int index){
		return this.numbers.remove(index);
	}
	
	/**
	 * Remove um Amigo da colecao.
	 * @param amigo
	 */
	
	public void removeAmigo(Amigo amigo){
		this.numbers.remove(amigo);
	}
	
	/**
	 * Quantidade de amigos na colecao.
	 * @return quantidade
	 */
	
	public int size(){
		return this.numbers.size();
	}
	
	/**
	 * Retorna um iterator
	 * @return iterator
	 */
	
	public Iterator<Amigo> iterator() {
		return numbers.iterator();
	}
	
}