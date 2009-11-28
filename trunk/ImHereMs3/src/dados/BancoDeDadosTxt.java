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

public class BancoDeDadosTxt {
	
	private static BancoDeDadosTxt instanciaUnica = null;
	public final String emailArq = "emails.log";
	public final String smsArq = "sms.log";
	public final String conviteArq = "convites.log";
	public String diretorioGabaritos = "gabaritos";
	private final String sep = System.getProperty("file.separator");
	
	private BancoDeDadosTxt() {}
	
	public static BancoDeDadosTxt getInstance() {
		if (instanciaUnica == null)
			instanciaUnica = new BancoDeDadosTxt();
		
		return instanciaUnica;
	}
	
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
	
	public boolean existeUsuario(String username) {
		File arquivo = new File("usuarios" + sep + username + ".txt");
		if (arquivo.exists()) {
			return true;
		}
		return false;
	}
	
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
	
	public void mudaDiretorioGabaritos(String novoDiretorio){
		this.diretorioGabaritos = novoDiretorio;
	}
	
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
