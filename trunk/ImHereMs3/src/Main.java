import java.util.Iterator;
import java.util.Scanner;

import principal.Sistema;
import util.EmailException;
import util.EntradaNoSistemaException;
import util.ErroLocalizacaoException;
import util.NomeException;
import util.SenhaException;
import util.UsernameException;
import util.Usuario;
import util.UsuarioException;

import com.maxmind.geoip.Location;


/**
 * Main do sistema.
 * 
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.0 13 de setembro de 2009
 */
public class Main {

	private static final String espacamento = "---------------------------------------------------------------------------------------";
	private static Sistema sistema;
	private static Usuario atualUsuario;
	private static boolean sistemaFuncionando = true;
	private static Location l1;

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);

		sistema = Sistema.getInstancia();

		System.out.println("Bem Vindo ao I'm Here!");

		while (sistemaFuncionando) {
			System.out.println(espacamento);
			System.out
					.println("                                          MENU");
			System.out.println(espacamento + "\n"
					+ "1 - CADASTRO\n2 - LOGIN\n3 - INFORMACOES\n4 - SAIR\n"
					+ espacamento);
			System.out.print("Entrada: ");
			String entradaDoMenu = entrada.nextLine();
			System.out.println(espacamento + "\n");

			if (entradaDoMenu.equalsIgnoreCase("1")) {
				boolean deuErro = false;
				try {
					atualUsuario = fazerCadastro(entrada);
				} catch (UsernameException e) {
					System.err
							.println("Username ja existe ou eh um dado obrigatorio.");
					deuErro = true;
				} catch (EmailException e) {
					System.err
							.println("E-mail invalido ou eh um dado obrigatorio.");
					deuErro = true;
				} catch (NomeException e) {
					System.err.println("Nome eh um dado obrigatorio.");
					deuErro = true;
				} catch (SenhaException e) {
					System.err
							.println("Senha eh um dado obrigatorio ou deve ter no minimo 6 caracteres.");
					deuErro = true;
				} catch (EntradaNoSistemaException e) {
					System.err.println("IP invalido.");
					deuErro = true;
				}

				if (!deuErro) {
					try {
						atualUsuario = sistema.fazerLogin(atualUsuario
								.getUsername(), atualUsuario.getSenha(),
								atualUsuario.getIP());
					} catch (EntradaNoSistemaException e) {
						System.err
								.println("Login/senha invalidos ou IP invalido.");
						deuErro = true;
					}
					if (!deuErro) {
						sistemaLogado(atualUsuario, entrada);
					}
				}
				voltarAoMenu(entrada);
			} else if (entradaDoMenu.equalsIgnoreCase("2")) {
				boolean deuErro = false;

				System.out
						.println("Para fazer login no sistema, preencha as informacoes que lhe sao pedidas corretamente.");
				System.out.println(espacamento + "\nLOGIN NO SISTEMA:\n"
						+ espacamento);

				System.out.print("Login: ");
				String login = entrada.nextLine();
				System.out.println(espacamento);

				System.out.print("Senha: ");
				String senha = entrada.nextLine();
				System.out.println(espacamento);

				String ip = gerarIP(entrada);
				try {
					atualUsuario = sistema.fazerLogin(login, senha, ip);
				} catch (EntradaNoSistemaException e) {
					System.err.println("Login/senha invalidos ou IP invalido.");
					deuErro = true;
				}
				if (!deuErro) {
					sistemaLogado(atualUsuario, entrada);
				}
				voltarAoMenu(entrada);
			} else if (entradaDoMenu.equalsIgnoreCase("3")) {
				System.out
						.println("                        Informacoes sobre o sistema I'm Here!");
				System.out.println(espacamento + "\n");
				System.out
						.println("       I'm Here é um sistema que permite compartilhamento de localização de pessoas.     \n"
								+ "  Os usuários só poderão visualizar a informação de localização uns dos outros se   \n"
								+ "  assim desejarem. Para que isso seja possível um convite de compartilhamento deve  \n"
								+ "  ser enviado a um usuário, o qual define se exibe ou oculta sua localização. Nesse \n"
								+ "  cenário, os usuários podem trocar mensagens entre si, seja um e-mail, um SMS ou   \n"
								+ "  até conversar por chat.                                                           ");
				System.out.println(espacamento + "\n");

				voltarAoMenu(entrada);
			} else if (entradaDoMenu.equalsIgnoreCase("4")) {
				sistemaFuncionando = false;
				System.exit(0);
			} else {
				System.err.println("Opcao invalida.\n");
			}
		}
	}

	/**
	 * Sistema funcionando para um user.
	 * 
	 * @param user
	 */
	private static void sistemaLogado(Usuario user, Scanner entrada) {

		localizacao(entrada, user);

		boolean sairDoSistemaLogin = false;
		boolean sairDoSistema = false;

		if (user.temConvites()) {
			System.out.println("Voce tem convites pendetes!");
			System.out
					.println("....................................................");
			Iterator<String> iterator = user.getConvites();
			Usuario amigo;
			while (iterator.hasNext()) {
				amigo = sistema.procurePorLogin(iterator.next());
				String escolha = null;
				entrada = new Scanner(System.in);
				System.out
						.println(amigo.getNome()
								+ " quer ser seu amigo. (qualquer coisa - nao, 1 - oculto, 2 - visivel.)");
				escolha = entrada.nextLine();
				if (escolha.equalsIgnoreCase("1")
						|| escolha.equalsIgnoreCase("2")) {
					sistema.confirmarCompartilhamento(user.getUsername(), amigo
							.getUsername(), Integer.parseInt(escolha));
					System.out.println("Compartilhamento feito com sucesso!");
					System.out
							.println("..............................................");
				} else {
					sistema.recusarCompartilhamento(user.getUsername(), amigo
							.getUsername());
				}
			}
		}
		
		user = sistema.procurePorLogin(user.getUsername());
		entrada = new Scanner(System.in);

		while (!sairDoSistemaLogin) {
			System.out.println("\nSistema Logado!");
			System.out
					.println(espacamento
							+ "\n                               O que voce deseja fazer?");
			System.out
					.println(espacamento
							+ "\n1 - Enviar convites"
							+ "\n2 - Ver meus amigos"
							+ "\n3 - Verificar se eu posso ver as informacoes de outra pessoa"
							+ "\n4 - Editar modo de compartilhamento com um amigo"
							+ "\n5 - Excluir amigo"
							+ "\n6 - Ver localizacao de amigos"
							+ "\n7 - Enviar email" + "\n8 - Enviar SMS"
							+ "\n9 - Chat"
							+ "\n\nPara voltar ao menu principal, digite 0");
			System.out.print(espacamento + "\nEntrada: ");
			String escolha = entrada.nextLine();
			System.out.println(espacamento);

			switch (Integer.parseInt(escolha)) {

			case 0:
				sairDoSistema = true;
				break;

			case 1:
				sistema.initMensageiro("email");

				String resposta = "s";
				while (resposta.equalsIgnoreCase("s")) {
					System.out.print("Email que voce vai mandar o convite: ");
					String email = entrada.nextLine();

					String mensagemEnvio = sistema.enviarConvite(user, email);
					if (sistema.enviouConvite) {
						System.out.println(mensagemEnvio);
					} else {
						System.err.println("Erro no envio do convite!");
					}
					System.out.print("Outro convite (S/N)? ");
					resposta = entrada.nextLine();
				}
				break;

			case 2:
				System.out.println("Lista de amigos de " + user.getNome()
						+ ":\n" + user.getListaAmigos().toString() + "\n");
				break;

			case 3:
				String amigoVer = usernameDoAmigo(entrada);

				try {
					if (sistema.possoVerLocalizacao(user.getUsername(),
							amigoVer)) {
						System.out.println("SIM");
					} else
						System.out.println("NAO");
				} catch (UsuarioException e) {
					System.err.println("Usuario desconhecido.");
				}
				break;

			case 4:
				int novoCompartilhamento = 0;
				String amigoEditar = usernameDoAmigo(entrada);

				System.out
						.print("Deseja OCULTAR (1) ou EXIBIR (2) as informacoes de "
								+ amigoEditar + "? (1/2): ");
				try {
					novoCompartilhamento = Integer.parseInt(entrada.nextLine());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				try {
						sistema.setCompartilhamento(user.getUsername(), amigoEditar,
								novoCompartilhamento);
					} catch (Exception e1) {
						System.out.println("Nao alterou");
					}
				break;

			case 5:
				boolean deuErro = false;
				String amigoRemover = usernameDoAmigo(entrada);
				try {
					sistema.removerAmigo(user.getUsername(), amigoRemover);
				} catch (UsuarioException e) {
					System.err
							.println("Permissao negada ou usuario desconhecido.");
					deuErro = true;
				}
				if (!deuErro) {
					System.out.println(amigoRemover + " removido com sucesso!");
				}
				break;

			case 6:
				String amigoVerLocal = usernameDoAmigo(entrada);
				try {
					System.out.println(sistema.getLocalizacaoAmigo(user
							.getUsername(), amigoVerLocal));
				} catch (UsuarioException e) {
					System.err
							.println("Permissao negada ou usuario desconhecido.");
					deuErro = true;
				}

				break;

			case 7:
				System.out.print("Email do amigo: ");
				String amigoEmail = entrada.nextLine();

				System.out.print("Assunto: ");
				String assunto = entrada.nextLine();

				System.out.print("Mensagem: ");
				String msgEmail = entrada.nextLine();

				System.out.println(sistema.enviarEmail(user.getUsername(),
						amigoEmail, assunto, msgEmail));
				break;

			case 8:
				System.out.print("Telefone do amigo: ");
				String amigoSMS = entrada.nextLine();

				System.out.print("Mensagem: ");
				String msgSMS = entrada.nextLine();

				System.out.println(sistema.enviarSMS(user.getUsername(),
						amigoSMS, msgSMS));
				break;

			case 9:
				System.out.print("Selecione a porta de conexão: ");
				String porta = entrada.nextLine();
				sistema.setPortChat(Integer.parseInt(porta));

				System.out.print("Username do us1: ");
				String amigo1 = entrada.nextLine();
				System.out.print("Username do us2: ");
				String amigo2 = entrada.nextLine();
				sistema.initChat(amigo1, amigo2);

				boolean chatEncerrou = false;
				String remetenteChat = "";
				String destinatarioChat = "";
				String mensagemChat = "";
				while (!chatEncerrou) {

					System.out.print("Remetente: ");
					remetenteChat = entrada.nextLine();

					System.out.print("Mensagem: ");
					mensagemChat = entrada.nextLine();

					if (mensagemChat.equals("") || remetenteChat.equals("")) {
						sistema.encerrarChat(amigo1, amigo2);
						chatEncerrou = true;
						break;
					}

					if (remetenteChat.equalsIgnoreCase(amigo1)) {
						destinatarioChat = amigo2;
					} else if (remetenteChat.equalsIgnoreCase(amigo2)) {
						destinatarioChat = amigo1;
					} else {
						System.err.println("ERRO! Remetente nao existe!");
					}
					sistema.enviarMensagem(destinatarioChat, mensagemChat);

				}
				break;

			default:
				System.err.println("ERRO! Digite novamente!");
				break;
			}

			if (!sairDoSistema) {
				System.out.print("Deseja voltar ao menu do sistema (S/N)? ");
				String resposta = entrada.nextLine();
				if (resposta.equalsIgnoreCase("N")) {
					sairDoSistemaLogin = true;
				}
			} else
				break;
		}
	}

	private static String usernameDoAmigo(Scanner entrada) {
		System.out.print("Username do amigo: ");
		String amigo = entrada.nextLine();

		return amigo;
	}

	private static void localizacao(Scanner entrada, Usuario user) {
		System.out.println("\n" + espacamento + "\nLOCALIZACAO NO SISTEMA:\n"
				+ espacamento);
		System.out
				.print("Voce deseja que sua localizacao seja feita automaticamente ou manualmente? (A/M): ");
		String escolhaLocate = entrada.nextLine();

		if (escolhaLocate.equalsIgnoreCase("A")) {
			try {
				l1 = sistema.obterLocalizacao(user);
			} catch (ErroLocalizacaoException e) {
				System.err.println("Nao foi possivel obter a localizacao.");
			}
		} else if (escolhaLocate.equalsIgnoreCase("M")) {
			System.out.print("Latitude: ");
			String latitude = entrada.nextLine();
			System.out.print("Longitude: ");
			String longitude = entrada.nextLine();

			try {
				sistema.setLocalizacao(user, Double.parseDouble(latitude),
						Double.parseDouble(longitude));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UsuarioException e) {
				System.err.println("O usuario nao existe.");
			} catch (ErroLocalizacaoException e) {
				System.err.println("Latitude/Longitude invalidos.");
			}
		}
	}

	/**
	 * Pergunta ao usuario se o mesmo deseja digitar seu IP manualmente ou se o
	 * sistema pode obte-lo automaticamente
	 * 
	 * @param entrada
	 * @return o ip
	 */
	private static String gerarIP(Scanner entrada) {
		System.out.print("Digite seu ip manualmente: ");
		String ip = entrada.nextLine();
		System.out.println(espacamento);
		return ip;
	}

	/**
	 * Pergunta ao usuario se o mesmo deseja voltar ao menu principal do sistema
	 * 
	 * @param entrada
	 */
	private static void voltarAoMenu(Scanner entrada) {
		System.out.print("Deseja voltar ao menu (S/N)? ");
		String resposta = entrada.nextLine();
		if (resposta.equalsIgnoreCase("N")) {
			sistemaFuncionando = false;
		}
	}

	/**
	 * Faz o cadastro de um usuario, que eh retornado
	 * 
	 * @param entrada
	 */
	private static Usuario fazerCadastro(Scanner entrada) {
		System.out
				.println("Para se cadastrar no sistema, preencha as informacoes que lhe sao pedidas corretamente.");
		System.out.println(espacamento + "\nCADASTRO:\n" + espacamento);

		System.out.print("Login: ");
		String login = entrada.nextLine();
		System.out.println(espacamento);

		System.out.print("Nome: ");
		String nome = entrada.nextLine();
		System.out.println(espacamento);

		System.out.print("Email: ");
		String email = entrada.nextLine();
		System.out.println(espacamento);

		System.out.print("Telefone: ");
		String telefone = entrada.nextLine();
		System.out.println(espacamento);

		System.out.print("Senha: ");
		String senha = entrada.nextLine();
		System.out.println(espacamento);

		String ip = gerarIP(entrada);

		atualUsuario = sistema
				.cadastro(login, nome, senha, email, telefone, ip);
		System.out.println("\nUsuario " + atualUsuario.getNome()
				+ " cadastrado com sucesso no sistema!\n");
		return atualUsuario;
	}
}
