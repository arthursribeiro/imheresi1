package dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import util.Usuario;
import util.VetorAmigo;

/**
 * Classe responsavel por armazenar arquivos na forma de txt.
 * Armazena e resgata os dados do sistema.
 * 
 * @author Arthur de Souza Ribeiro
 * @author José Laerte Pires Xavier Junior
 * @author Raquel Rolim Almeida Guimarães
 * @author Raíssa de Araújo Jorge e Menezes Sarmento
 *
 */

public class BancoDeDadosTxt {
	
	private static BancoDeDadosTxt instanciaUnica = null;
	public final String emailArq = "emails.log";
	public final String smsArq = "sms.log";
	public final String conviteArq = "convites.log";
	public String diretorioGabaritos = "gabaritos";
	private final String sep = System.getProperty("file.separator");
	
	private BancoDeDadosTxt() {}
	
	/**
	 * Método para garantir instacia única(singleton)
	 * @return BancoDeDadosTxt única instancia
	 */
	public static BancoDeDadosTxt getInstance() {
		if (instanciaUnica == null)
			instanciaUnica = new BancoDeDadosTxt();
		
		return instanciaUnica;
	}
	
	/**
	 * metodo para atualizar o usuario no banco de dados
	 * 
	 * @param usuario objeto usuario
	 * @return true se conseguir atualizar, false caso contrário
	 */
	public boolean atualizarUsuario(Usuario usuario) {
		if(existeUsuario(usuario.getUsername())) {
			try {
				FileOutputStream fos = new FileOutputStream("usuarios" + sep + usuario.getUsername() + ".txt");
				ObjectOutputStream oos= new ObjectOutputStream(fos);
				oos.writeObject(usuario);
				oos.close();
				return true;
			} catch (IOException e) {}
		}
		return false;
	}
	
	/**
	 * Metodo para cadastrar o usuario no banco de dados (XML)
	 * 
	 * @param username nome de usuario
	 * @param nome nome completo do usuario
	 * @param senha senha de acesso
	 * @param email email do usuario
	 * @param telefone telefone do usuario
	 * @param ip ip do usuario
	 * @return objeto usuario
	 */
	public Usuario cadastraUsuario(String username, String nome, String senha, String email, String telefone, String ip) {
		Usuario usuario = new Usuario(username, nome, senha, email, telefone, ip);
		try {
			FileOutputStream fos = new FileOutputStream("usuarios" + sep + usuario.getUsername() + ".txt");
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(usuario);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	/**
	 * metodo para verificar se existe um usuario com o username passado
	 * @param username nome de usuario a procurar
	 * @return true se existe, false se nao
	 */
	public boolean existeUsuario(String username) {
		File arquivo = new File("usuarios" + sep + username + ".txt");
		if (arquivo.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * metodo para procurar usuario no banco de dados, pelo login
	 * @param login login do usuario a ser procurado
	 * @return o usuario procurado, ou null se nao encontrar
	 */
	public Usuario procureUsuarioLogin(String login) {
		try {
			FileInputStream fis = new FileInputStream("usuarios" + sep + login + ".txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Usuario usuario = (Usuario) ois.readObject();
			ois.close();
			return usuario;
		} catch (Exception e) {}
		
		return null;
	}
	
	/**
	 * metodo para procurar usuarios pelo nome
	 * @param nome nome a procurar
	 * @return lista com os usuarios que tem o nome
	 */
	public ArrayList<Usuario> procureUsuariosNome(String nome) {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		File pasta = new File("usuarios" + sep);  
		File arrayArquivos[] = pasta.listFiles();  
		
		for (File arquivo : arrayArquivos) {
			Usuario usuario = null;
			try {
				if(arquivo.getName().compareTo(".svn") != 0) {
				    FileInputStream fis = new FileInputStream("usuarios" + sep + arquivo.getName());
				    ObjectInputStream ois = new ObjectInputStream(fis);
				    usuario = (Usuario) ois.readObject();
				    ois.close();
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (usuario != null && usuario.getNome().contains(nome)) {
				listaUsuarios.add(usuario);
			}
		}
		return listaUsuarios;
	}
	
	/**
	 * metodo para apagar um usuario do banco de dados, pelo login
	 * @param login login para procurar o usuario a remover
	 */
	public void removerUsuario(String login) {
		File arquivoARemover = new File("usuarios" + sep + login + ".txt");
		
		if (arquivoARemover.exists()) {
			arquivoARemover.delete();
		}
		
		// varrer para apagar das listas de amigos
		File pasta = new File("usuarios" + sep);  
		File arrayArquivos[] = pasta.listFiles();  
		
		for (File arquivo : arrayArquivos) {
			Usuario usuario = null;
			try {
				if(arquivo.getName().compareTo(".svn") != 0) {
					FileInputStream fis = new FileInputStream("usuarios" + sep + arquivo.getName());
					ObjectInputStream ois = new ObjectInputStream(fis);
					usuario = (Usuario) ois.readObject();
					ois.close();
					
					VetorAmigo amigos = usuario.getListaAmigos();
					for (int i = 0; i < amigos.size() ; i++) {
						if (procureUsuarioLogin(amigos.getAmigo(i).getUsuario()).getUsername().equalsIgnoreCase(login))
							amigos.removeAmigo(amigos.getAmigo(i));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * metodo para apagar os dados persistentes do banco de dados
	 */
	public void zereOBancoDeDados() {

		//isso eh pra apagar os logs de chats
		File pastaRaiz = new File("." + sep);  
		File ArquivosRaiz[] = pastaRaiz.listFiles(); 
		for (File arquivo : ArquivosRaiz) {
			if (arquivo.getName().endsWith(".log"))
				arquivo.delete();
		}
		
		File pasta = new File("usuarios" + sep);  
		File arrayArquivos[] = pasta.listFiles(); 
		
		for (File arquivo : arrayArquivos) {
			arquivo.delete();
		}
	}
	
	/**
	 * metodo para verificar se existe um usuario com o username passado
	 * @param username nome de usuario a procurar
	 * @return true se existe, false se nao
	 */
	public Usuario procureUsuarioEmail(String email) {
		File pasta = new File("usuarios" + sep);  
		File arrayArquivos[] = pasta.listFiles();  
		
		for (File arquivo : arrayArquivos) {
			if (arquivo.getName().contains(".txt")) {
				try {
					if(arquivo.getName().compareTo(".svn") != 0) {
						FileInputStream fis = new FileInputStream("usuarios" + sep + arquivo.getName());
						ObjectInputStream ois = new ObjectInputStream(fis);
						Usuario usuario = (Usuario) ois.readObject();
						ois.close();
						
						if (usuario.getEmail().equalsIgnoreCase(email)) {
							return usuario;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * seta o diretorio onde esta guardado o arquivo gabarito para os convites
	 * @param novoDiretorio diretorio a ser setado
	 */
	public void mudaDiretorioGabaritos(String novoDiretorio){
		this.diretorioGabaritos = novoDiretorio;
	}
	
	/**
	 * metodo para pegar a lista de todos os usuarios cadastrados no sistema
	 * @return lista com os usuarios
	 */
	public ArrayList<Usuario> getUsuarios() {
		File pasta = new File("usuarios" + sep);  
		File arrayArquivos[] = pasta.listFiles();  
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		for (File arquivo : arrayArquivos) {
			if (arquivo.getName().contains(".txt")) {
				try {
					FileInputStream fis = new FileInputStream("usuarios" + sep + arquivo.getName());
					ObjectInputStream ois = new ObjectInputStream(fis);
					usuarios.add((Usuario) ois.readObject());
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return usuarios;
	}

	/**
	 * metodo usado para guardar as mensagens em arquivos persistentes
	 * @param conteudo string com o conteudo a ser guardado
	 * @param nomeDoArquivo nome do arquivo onde guardar o conteudo
	 */
	public void guardarMensagem(String conteudo, String nomeDoArquivo) {
		try {
			FileWriter writer = new FileWriter(nomeDoArquivo, true);
			writer.write(conteudo);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
