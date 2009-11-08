package principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Amigo;
import util.Chat;
import util.Convite;
import util.Email;
import util.EmailException;
import util.EntradaNoSistemaException;
import util.ErroLocalizacaoException;
import util.Localizacao;
import util.LoginException;
import util.Mensageiro;
import util.Mensagem;
import util.NomeException;
import util.SMS;
import util.SenhaException;
import util.UsernameException;
import util.Usuario;
import util.UsuarioException;
import util.VetorAmigo;


import com.maxmind.geoip.Location;

import dados.BancoDeDados;


/**
 * Classe Sistema. Eh ele quem armazena as informacoes dos usuarios no arquivo
 * XML, quem recupera essas informacoes, quem cadastra novos usuarios, remove
 * usuarios, atyaliza informacoes dos usuarios...
 * 
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 2.1 13 de setembro de 2009
 */

public class Sistema {

	private static Sistema instanciaUnica = null;
	private BancoDeDados bancoDeDados;
	Localizacao localizacao;
	Mensagem mensagem;
	Mensageiro mensageiro;
	boolean ativaConvite;
	public boolean enviouConvite;
	boolean chatOn;
	private int portaChat;
	boolean localizacaoAuto;

	/**
	 * Construtor do Sistema. Ele recupera todos os usuarios cadastrados e
	 * armazena-os numa lista de Usuarios
	 */
	protected Sistema() {
		bancoDeDados = BancoDeDados.getInstancia();
		localizacao = new Localizacao();
		chatOn = false;
		mensageiro = new Mensageiro(bancoDeDados);

		localizacaoAuto = false;
		enviouConvite = false;
	}
	
	public static Sistema getInstancia(){
		if (instanciaUnica == null){
		       instanciaUnica = new Sistema();
		     }
		     return instanciaUnica;

	}

	/**
	 * Seta o tipo de mensagem a ser enviada.
	 * 
	 * @param tipoDeMensagem
	 */
	public void initMensageiro(String tipoDeMensagem) {
		if (tipoDeMensagem.equalsIgnoreCase("email")) {
			/**
			 * ativaEmail = true; ativaChat = false; ativaSms = false;
			 */

			ativaConvite = false;
			mensagem = new Email();
		} else if (tipoDeMensagem.equalsIgnoreCase("chat")) {
			/**
			 * ativaChat = true; ativaSms = false; ativaConvite = false;
			 * ativaEmail = false;
			 */

			ativaConvite = false;
			mensagem = new Chat();
		} else if (tipoDeMensagem.equalsIgnoreCase("sms")) {
			/**
			 * ativaSms = true; ativaConvite = false; ativaEmail = false;
			 * ativaChat = false;
			 */

			ativaConvite = false;
			mensagem = new SMS();
		} else if (tipoDeMensagem.equalsIgnoreCase("convite")) {
			/**
			 * ativaConvite = true; ativaEmail = false; ativaChat = false;
			 * ativaSms = false;
			 */

			ativaConvite = true;
			mensagem = new Convite();
		}
	}

	/**
	 * Cadastra um usuario no sistema.
	 * 
	 * @param username
	 * @param nome
	 * @param senha
	 * @param email
	 * @param telefone
	 * @param ip
	 */
	public Usuario cadastro(String username, String nome, String senha,
			String email, String telefone, String ip) {
		if (username.equalsIgnoreCase("")) {
			throw new UsernameException("Username eh um dado obrigatorio.");
		} else if (nome.equalsIgnoreCase("")) {
			throw new NomeException("Nome eh um dado obrigatorio.");
		} else if (email.equalsIgnoreCase("")) {
			throw new EmailException("E-mail eh um dado obrigatorio.");
		} else if (senha.equalsIgnoreCase("")) {
			throw new SenhaException("Senha eh um dado obrigatorio.");
		} else if (!emailValido(email)) {
			throw new EmailException("E-mail invalido.");
		} else if (!senhaValida(senha)) {
			throw new SenhaException("Senha deve ter no minimo 6 caracteres.");
		} else if (!ipValido(ip)) {
			throw new EntradaNoSistemaException("IP invalido.");
		} else if (bancoDeDados.existeUsuario(username)) {
			throw new UsernameException("O username jah existe.");
		}
		return bancoDeDados.cadastraUsuario(username, nome, senha, email,
				telefone, ip);
	}
	
	/**
	 * Verifica se a senha eh valida.
	 * 
	 * @param senha
	 * @return boolean
	 */
	private boolean senhaValida(String senha) {
		if (senha.length() >= 6) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se um email eh valido.
	 * 
	 * @param email
	 * @return boolean
	 */
	private boolean emailValido(String email) {
		boolean valido = false;
		int quantidade = 0;
		
		for (int i = 0; i < email.length(); i++) {
			if ((email.substring(i, i + 1)).equalsIgnoreCase("@")) {
				quantidade++;
				if (i != 0 && i != email.length() - 1) {
					valido = true;
				}
			}
		}
		if (quantidade > 1) {
			valido = false;
		}
		if (existeEmail(email)) {
			valido = false;
		}
		return valido;
	}

	private boolean existeEmail(String email2) {
		ArrayList<Usuario> usuarios = bancoDeDados.getUsuarios();
		Iterator<Usuario> iterUsuarios = usuarios.iterator();
		
		int i = 0;
		while(iterUsuarios.hasNext()){
			if(usuarios.get(i).getEmail().equalsIgnoreCase(email2))
				return true;
			i++; iterUsuarios.next();
		}
		return false;
	}

	/**
	 * Apaga todos os dados do sistema.
	 */
	public void zerarOSistema() {
		bancoDeDados.zereOBancoDeDados();
	}

	/**
	 * Procura um usuario atraves de seu login.
	 * 
	 * @param login
	 * @return usuario
	 */
	public Usuario procurePorLogin(String login) {
		if (login.equalsIgnoreCase("") || !bancoDeDados.existeUsuario(login)) {
			throw new UsuarioException("O usuario nao existe.");
		}
		return bancoDeDados.procureUsuarioLogin(login);
	}

	/**
	 * Procura um usuario atraves do seu nome.
	 * @param nome
	 * @return lista de usuarios.
	 */
	public ArrayList<Usuario> procurePorNome(String nome) {
		if (nome.equalsIgnoreCase("")) {
			throw new UsuarioException("O usuario nao existe.");
		}
		return bancoDeDados.procureUsuariosNome(nome);
	}

	/**
	 * Remove um usuario.
	 * 
	 * @param login
	 */
	public void remove(String login) {
		if (!bancoDeDados.existeUsuario(login)) {
			throw new UsuarioException("O usuario nao existe.");
		} else {
			bancoDeDados.removerUsuario(login);
		}
	}

	/**
	 * Atualiza dados de um usuario passado como parametro.
	 * 
	 * @param usuario
	 * @param atributo
	 * @param valor
	 * @return boolean
	 */
	public boolean atualizaDadosUsuario(String usuario, String atributo,
			String valor) {
		if (atributo.equalsIgnoreCase("username")) {
			throw new UsernameException("Nao eh permitido alterar o username.");
		} else if (!bancoDeDados.existeUsuario(usuario)) {
			throw new UsuarioException("O usuario nao existe.");
		} else if (atributo.equalsIgnoreCase("nome")
				&& valor.equalsIgnoreCase("")) {
			throw new NomeException("Nome eh um dado obrigatorio.");
		} else if (atributo.equalsIgnoreCase("senha") && !senhaValida(valor)) {
			throw new SenhaException("Senha deve ter no minimo 6 caracteres.");
		} else if (atributo.equalsIgnoreCase("email")
				&& existeEmail(valor)) {
			throw new EmailException("E-mail invalido.");
		} else if (atributo.equalsIgnoreCase("email") && !emailValido(valor)) {
			throw new EmailException("E-mail invalido.");
		} else {
			Usuario novoUsuario = bancoDeDados.procureUsuarioLogin(usuario);
			if (atributo.equalsIgnoreCase("nome")) {
				novoUsuario.setNome(valor);
			}
			else if (atributo.equalsIgnoreCase("senha")) {
				novoUsuario.setSenha(valor);
			}
			else if (atributo.equalsIgnoreCase("email")) {
				novoUsuario.setEmail(valor);
			}
			else if (atributo.equalsIgnoreCase("telefone")) {
				novoUsuario.setTelefone(valor);
			}
			else if (atributo.equalsIgnoreCase("ip")) {
				novoUsuario.setIP(valor);
			}
			else if (atributo.equalsIgnoreCase("latitude")) {
				novoUsuario.setLatitude(valor);
			}
			else if (atributo.equalsIgnoreCase("longitude")) {
				novoUsuario.setLongitude(valor);
			}
			else if (atributo.equalsIgnoreCase("estahConectado")) {
				if (valor.equalsIgnoreCase("sim")) {
					novoUsuario.setEstaConectado(true);
				}
				else {
					novoUsuario.setEstaConectado(false);
				}
			}
			return bancoDeDados.atualizarUsuario(novoUsuario);
		}
	}

	/**
	 * Fazer login de um usuario no sistema.
	 * 
	 * @param login
	 * @param senha
	 * @param ip
	 * @return usuario
	 */
	public Usuario fazerLogin(String login, String senha, String ip) {
		if (ipValido(ip)) {
			try {
				Usuario usuario = procurePorLogin(login);
				if (usuario != null) {
					if (usuario.compareSenha(senha)) {
						usuario.setIP(ip);
						usuario.setEstaConectado(true);
						bancoDeDados.atualizarUsuario(usuario);
						return usuario;
					} else {
						throw new EntradaNoSistemaException(
								"Login/senha invalidos.");
					}
				}
			} catch (UsuarioException e) {
				throw new EntradaNoSistemaException("Login/senha invalidos.");
			}
		}
		throw new EntradaNoSistemaException("IP invalido.");
	}

	/**
	 * Faz o logout de um usuario
	 * 
	 * @param atualUsuario
	 */
	public void logout(Usuario atualUsuario) {
		if (atualUsuario == null || !atualUsuario.getEstaConectado()) {
			throw new EntradaNoSistemaException("Sessao inexistente.");
		} else {
			atualUsuario.setEstaConectado(false);
			bancoDeDados.atualizarUsuario(atualUsuario);
		}
	}

	/**
	 * Retorna um boolean resultante da verificacao se o ip eh valido.
	 * 
	 * @param ip
	 * @return boolean
	 */
	private boolean ipValido(String ip) {

		String[] nSep = separe(ip);
		if (nSep.length != 4) {
			return false;
		}
		try {
			for (int i = 0; i < nSep.length; i++) {
				if (Integer.parseInt(nSep[i]) > 255
						&& Integer.parseInt(nSep[i]) < 0) {
					return false;
				}
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Separa atraves dos pontos, um ip numa lista que eh retornada.
	 * 
	 * @param ip
	 * @return lista
	 */
	private String[] separe(String ip) {
		String[] lista = new String[0];
		String numero = "";
		for (int i = 0; i < ip.length(); i++) {
			if (ip.substring(i, i + 1).equalsIgnoreCase(".")) {
				String[] novaLista = new String[lista.length + 1];
				lista = novaLista;
			}
		}
		String[] novaLista = new String[lista.length + 1];
		lista = novaLista;
		int tamanho = 0;
		for (int j = 0; j < ip.length(); j++) {
			if (ip.substring(j, j + 1).equalsIgnoreCase(".")) {
				lista[tamanho] = numero;
				tamanho++;
				numero = "";
			} else {
				numero += ip.subSequence(j, j + 1);
			}
		}
		lista[tamanho] = numero;
		return lista;
	}

	/**
	 * Retorna um boolean resultante da verificacao se o usuario esta ou nao
	 * conectado
	 * 
	 * @param usuario
	 * @return boolean
	 */
	public boolean estahConectado(Usuario usuario) {
		return usuario.getEstaConectado();
	}

	/**
	 * Modifica a latitude e longitude manualmente.
	 * 
	 * @param usuario
	 * @param latitude
	 * @param longitude
	 */
	public void setLocalizacao(Usuario usuario, double latitude,
			double longitude) {
		if (!bancoDeDados.existeUsuario(usuario.getUsername())) {
			throw new UsuarioException("O usuario nao existe.");
		}
		if (verifiqueLatLong(latitude, longitude)) {
			usuario.setLatitude(latitude + "");
			usuario.setLongitude(longitude + "");
			bancoDeDados.atualizarUsuario(usuario);
		} else {
			throw new ErroLocalizacaoException("Latitude/Longitude invalidos.");
		}
	}

	/**
	 * Verifica se a latitude e longitude sao validos.
	 * 
	 * @param latitude1
	 * @param longitude1
	 * @return boolean
	 */
	public boolean verifiqueLatLong(double latitude1, double longitude1) {
		if (latitude1 >= -90 && latitude1 <= 90) {
			if (longitude1 >= -180 && longitude1 <= 180) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna uma string com os tipos de localizadores.
	 * 
	 * @return string
	 */
	public String getLocalizadores() {
		return localizacaoAuto ? "[GeoIP, Auto]" : "[GeoIP, Manual]";
	}

	/**
	 * Obtem a localizacao do usuario atraves do IP.
	 * 
	 * @param usuario
	 * @return objeto location
	 */
	public Location obterLocalizacao(Usuario usuario) {
		localizacaoAuto = true;
		if (usuario != null) {
			if (bancoDeDados.existeUsuario(usuario.getUsername())) {
				String ip = getIPUsuario(usuario);
				Location l = localizacao.localize(ip);
				if (l == null) {
					throw new ErroLocalizacaoException(
							"Nao foi possivel obter a localizacao.");
				}
				usuario.setLatitude("" + l.latitude);
				usuario.setLongitude("" + l.longitude);
				bancoDeDados.atualizarUsuario(usuario);
				return l;
			}
		}
		throw new UsuarioException("O usuario nao existe.");
	}

	private String getIPUsuario(Usuario usuario) {
		return bancoDeDados.procureUsuarioLogin(usuario.getUsername()).getIP();
	}

	// us3
	/**
	 * Retorna a localizacao de um usuario
	 * 
	 * @param login
	 *            do usuario
	 * @return localizacao
	 */
	public String getLocalizacao(String login) {
		Usuario usuario = bancoDeDados.procureUsuarioLogin(login);
		return "Lat: " + usuario.getLatitude() + ", Long: "
				+ usuario.getLongitude();
	}

	/**
	 * Envia um convite de um usuario para um email.
	 * 
	 * @param usuario
	 * @param email
	 * @return boolean
	 */
	public String enviarConvite(Usuario usuario, String email) {
		if (usuario == null || !bancoDeDados.existeUsuario(usuario.getUsername())) {
			return null;
		}
		
		if (!ativaConvite) {
			initMensageiro("convite");
		}

		mensageiro.enviarMensagem(this.mensagem, usuario.getUsername(),email, "");
		enviouConvite = true;
		return "Documento convite.txt enviado com sucesso.";
	}

	/**
	 * Seta o diretorio de modelos de mensagens do sistema.
	 * 
	 * @param novoDiretorio
	 */
	public void setDiretorioGabaritos(String novoDiretorio) {
		bancoDeDados.mudaDiretorioGabaritos(novoDiretorio);
	}

	/**
	 * envia um email.
	 * 
	 * @param loginRemetente
	 * @param loginDestinatario
	 * @param assunto
	 * @param msg
	 * @return resultado
	 */
	public String enviarEmail(String loginRemetente, String emailDestinatario,
			String assunto, String msg) {
		initMensageiro("email");
		mensageiro.enviarMensagem(mensagem, loginRemetente, emailDestinatario,
				msg, assunto);
		return "Email enviado com sucesso.";
	}

	/**
	 * envia sms
	 * 
	 * @param loginRemetente
	 * @param loginDestinatario
	 * @param msg
	 * @return resultado
	 */
	public String enviarSMS(String loginRemetente, String loginDestinatario,
			String msg) {
		initMensageiro("sms");
		mensageiro.enviarMensagem(this.mensagem, loginRemetente,
				loginDestinatario, msg);
		return "SMS enviado com sucesso.";
	}

	/**
	 * Inicializa um chat de login1 com logn2
	 * 
	 * @param login1
	 * @param login2
	 */
	public void initChat(String login1, String login2) {
		if (!chatOn) {
			initMensageiro("chat");
			chatOn = true;
			Chat chat = (Chat) this.mensagem;
			chat.setUsuario1(login1);
			chat.setUsuario2(login2);
		}
	}

	/**
	 * Seta a porta do chat. Lorena disse que para um fake de chat isso nao tem
	 * serventia.
	 * 
	 * @param port
	 */
	public void setPortChat(int port) {
		this.portaChat = port;
	}

	/**
	 * Envia uma msg do chat que esta ligado.
	 * 
	 * @param loginDestinario
	 * @param msg
	 */
	public void enviarMensagem(String loginDestinario, String msg) {
		Chat chat = (Chat) this.mensagem;
		chat.enviePara(loginDestinario, msg);
	}

	/**
	 * recebe uma msg do chat que esta ligado.
	 * 
	 * @param login
	 * @return mensagem
	 */
	public String receberMensagem(String login) {
		Chat chat = (Chat) this.mensagem;
		return chat.getMsg(login);
	}

	/**
	 * Encerra um chat
	 * 
	 * @param login1
	 * @param login2
	 */
	public void encerrarChat(String login1, String login2) {
		Chat chat = (Chat) this.mensagem;
		
		if (login1.equalsIgnoreCase(chat.getUsuario1())
				&& login2.equalsIgnoreCase(chat.getUsuario2())) {
			mensageiro.enviarMensagem(chat, login1, login2, "");
			
			
			chat.encerreChat();
			chatOn = false;
		}
	}

	/**
	 * Confirma o compartilhamento entre dois usuarios.
	 * 
	 * @param convidado
	 *            pessoa que aceita o convite
	 * @param remetente
	 *            pessoa que tinha enviado o convite
	 * @param modo
	 *            modo com que o convidado aceitou o convite
	 */
	public void confirmarCompartilhamento(String convidado, String remetente,
			int modo) {
		Usuario userConvidado = bancoDeDados.procureUsuarioLogin(convidado);
		Usuario userRemetente = bancoDeDados.procureUsuarioLogin(remetente);
		userConvidado.deletarConvite(remetente);
		
		if (!userConvidado.ehAmigoDe(userRemetente.getUsername())) {
			userConvidado.adicionarAmigo(userRemetente, 2);
			userRemetente.adicionarAmigo(userConvidado, modo);
		}
		bancoDeDados.atualizarUsuario(userRemetente);
		bancoDeDados.atualizarUsuario(userConvidado);
	}

	/**
	 * Recusa o compartilhamento de um usuario com outro.
	 * 
	 * @param username1
	 * @param username2
	 */
	public void recusarCompartilhamento(String username1, String username2) {
		Usuario user1 = bancoDeDados.procureUsuarioLogin(username1);
		user1.removerAmigo(username2);
		bancoDeDados.atualizarUsuario(user1);
	}

	/**
	 * Retorna uma string da lista de amigos do usuario passado como parametro
	 * se este estiver conectado.
	 * 
	 * @param usuario
	 * @return lista de amigos
	 */
	public String getAmigos(Usuario usuario) {
		if (usuario.getEstaConectado()) {
			VetorAmigo listaDeAmigos = usuario.getListaAmigos();
			ArrayList<String> listaDeString = new ArrayList<String>();
			
			Iterator<Amigo> iterListaDeAmigos = listaDeAmigos.iterator();
			
			Amigo a;
			while(iterListaDeAmigos.hasNext()){
				a = iterListaDeAmigos.next();
				listaDeString.add(procurePorLogin(a.getUsuario())
						.getNome());
			}
			
			Iterator<String> iterator = ordene(listaDeString).iterator();
			
			String separador = ", ";
			String string = "[";
			
			String s;
			while(iterator.hasNext()){
				s = iterator.next();
				if (iterator.hasNext()) {
					string += s + separador;
				} else {
					string += s + "]";
				}
			}
			return string;
		}
		throw new LoginException("Permissao negada.");
	}


	/**
	 * Ordena uma lista passada como parametro.
	 * 
	 * @param lista
	 * @return
	 */
	private ArrayList<String> ordene(List<String> lista) {
		String[] novaLista = new String[lista.size()];
		Iterator<String> iter = lista.iterator();
		
		int cursor = 0;
		while(iter.hasNext()){
			novaLista[cursor] = lista.get(cursor);
			cursor++; iter.next();
		}
		
		for (int i = 0; i < novaLista.length - 1; i++) {
			for (int j = 0; j < novaLista.length - 1; j++) {
				if (novaLista[j].compareToIgnoreCase(novaLista[j + 1]) > 0) {
					String prov = novaLista[j];
					novaLista[j] = novaLista[j + 1];
					novaLista[j + 1] = prov;
				}
			}
		}
		ArrayList<String> l = new ArrayList<String>();
		for (int i = 0; i < novaLista.length; i++) {
			l.add(novaLista[i]);
		}
		return l;
	}

	// VAI SERVIR PARA DELANO IMPLEMENTAR O ITEM 5 => NUMERO MAXIMO DE
	// AMIGOS!!NÃO PERMITIR QUE ELE CONVIDE ALGUEM QUE JA TENHA O NUMERO
	// MAXIMO...OU SE ELE JA TIVER O NUMERO MAXIMO!!
	/**
	 * Retorna o numero de amigos de um usuario
	 * 
	 * @param login
	 * @return numero de amigos
	 */
	public int getNumeroDeAmigos(String login) {
		if(bancoDeDados.existeUsuario(login))
			return bancoDeDados.procureUsuarioLogin(login).getListaAmigos().size();
		return 0;
	}

	/**
	 * Verifica se login1 pode ver a localizacao de loginAmigo
	 * 
	 * @param login
	 * @param loginAmigo
	 * @return
	 */
	public boolean possoVerLocalizacao(String login, String loginAmigo) {
		Usuario usuario = bancoDeDados.procureUsuarioLogin(login);
		VetorAmigo lista = usuario.getListaAmigos();
		Iterator<Amigo> iterLista = lista.iterator();
		
		Amigo a;
		while(iterLista.hasNext()){
			a = iterLista.next();
			if(a.getUsuario().equalsIgnoreCase(loginAmigo)){
				if (a.getModo() == 2) {
					return true;
				} else {
					return false;
				}
			}
		}
		throw new UsuarioException("Usuario desconhecido.");
	}

	/**
	 * Modifica a forma de compartilhamento de login1 com login2
	 * 
	 * @param login1
	 * @param login2
	 * @param modo
	 */
	public void setCompartilhamento(String login1, String login2, int modo) {
		Usuario usuario = bancoDeDados.procureUsuarioLogin(login2);
		usuario.modificarCompartilhamento(login1, modo);
		bancoDeDados.atualizarUsuario(usuario);
	}

	/**
	 * Remove um amigo da lista de login1
	 * 
	 * @param login1
	 * @param login2
	 */
	public void removerAmigo(String login1, String login2) {
		if (!bancoDeDados.existeUsuario(login1)) {
			throw new UsuarioException("Permissao negada.");
		}
		Usuario usuario1 = bancoDeDados.procureUsuarioLogin(login1);
		Usuario usuario2 = bancoDeDados.procureUsuarioLogin(login2);
		VetorAmigo lista = usuario1.getListaAmigos();
		if (!contem(lista, login2)) {
			throw new UsuarioException("Usuario desconhecido.");
		}
		usuario1.removerAmigo(login2);
		usuario2.removerAmigo(login1);
		bancoDeDados.atualizarUsuario(usuario1);
		bancoDeDados.atualizarUsuario(usuario2);
	}

	/**
	 * Verifica se um login esta na lista.
	 * 
	 * @param lista
	 * @param login2
	 * @return
	 */
	private boolean contem(VetorAmigo lista, String login2) {
		Iterator<Amigo> iter = lista.iterator();
		
		Amigo a;
		while(iter.hasNext()){
			a = iter.next();
			if (procurePorLogin(a.getUsuario()).getUsername()
					.equalsIgnoreCase(login2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna um amigo de uma lista de amigos.
	 * 
	 * @param lista
	 * @param login
	 * @return
	 */
	private Amigo retorneAmigo(VetorAmigo lista, String login) {
		Iterator<Amigo> iter = lista.iterator();
		Amigo a;
		while(iter.hasNext()){
			a = iter.next();
			if (a.getUsuario().equalsIgnoreCase(login)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * retorna a localizacao de um amigo.
	 * 
	 * @param login
	 * @param loginAmigo
	 * @return
	 */
	public String getLocalizacaoAmigo(String login, String loginAmigo) {
		if (bancoDeDados.existeUsuario(login)) {
			Usuario usuario = bancoDeDados.procureUsuarioLogin(login);
			VetorAmigo lista = usuario.getListaAmigos();
			if (contem(lista, loginAmigo)) {
				Amigo amigo = retorneAmigo(lista, loginAmigo);
				if (amigo.getModo() == 2) {
					return getLocalizacao(procurePorLogin(amigo.getUsuario()).getUsername());
				} else {
					return "Localizacao desconhecida.";
				}
			}

			throw new UsuarioException("Usuario desconhecido.");
		}

		throw new UsuarioException("Permissao negada.");
	}

	/**
	 * Gera o IP automaticamente
	 */
	/*
	 * public String gerarIPauto() { gerouIPauto = true; try{ URL url = new
	 * URL("http://whatismyip.com/automation/n09230945.asp"); HttpURLConnection
	 * conexao = (HttpURLConnection) url.openConnection(); conexao.connect();
	 * java.io.BufferedReader pagina = new java.io.BufferedReader(new
	 * java.io.InputStreamReader(conexao.getInputStream())); String meuIP =
	 * pagina.readLine(); pagina.close(); return meuIP; }catch (Exception e){
	 * e.printStackTrace(); } return null;
	 * 
	 * }
	 */

	public BancoDeDados getBanco() {
		return this.bancoDeDados;
	}
}