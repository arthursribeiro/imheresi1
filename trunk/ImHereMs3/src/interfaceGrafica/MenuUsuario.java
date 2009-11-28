package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import util.Usuario;

public class MenuUsuario extends JFrame implements ActionListener {

	private JButton botaoEnviarConvite;
	private JButton botaoLogOut;
	private JButton botaoVerAmigos;
	private JButton botaoVerificarModo;
	private JButton botaoEditarModo;
	private JButton botaoExcluirAmigo;
	private JButton botaoVerLocal;
	private JButton botaoEnviarEmail;
	private JButton botaoEnviarSMS;
	private JButton botaoAceitarComp;
	private Usuario usuario;
	private static MenuUsuario instanciaUnica;

	private MenuUsuario() {
		super("Menu do Usuário");
		initComponents();
	}

	public void setUsuario(Usuario u) {
		usuario = u;
	}

	public static MenuUsuario getInstancia() {
		if (instanciaUnica == null)
			instanciaUnica = new MenuUsuario();
		return instanciaUnica;
	}

	private void initComponents() {
		JButton[] botoes = {
				botaoEnviarConvite = new JButton("Enviar Convite"),
				botaoVerAmigos = new JButton("Ver Amigos"),
				botaoVerificarModo = new JButton("Modo de Compartilhamento"),
				botaoEditarModo = new JButton("Editar Modo de Compartilhamento"),
				botaoExcluirAmigo = new JButton("Excluir Amigo"),
				botaoVerLocal = new JButton("Localização de Amigos"),
				botaoEnviarEmail = new JButton("Enviar Email"),
				botaoEnviarSMS = new JButton("Enviar SMS"),
				botaoAceitarComp = new JButton("Aceitar Compartilhamento"),
				botaoLogOut = new JButton("LogOut") };

		botaoEnviarConvite.setActionCommand("convite");
		botaoVerAmigos.setActionCommand("amigos");
		botaoVerificarModo.setActionCommand("compartilhamento");
		botaoEditarModo.setActionCommand("editar");
		botaoExcluirAmigo.setActionCommand("excluir");
		botaoVerLocal.setActionCommand("localizar");
		botaoEnviarEmail.setActionCommand("email");
		botaoEnviarSMS.setActionCommand("sms");
		botaoAceitarComp.setActionCommand("aceitarComp");
		botaoLogOut.setActionCommand("logout");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);

		JLabel imagem = new JLabel();
		imagem.setIcon(Images.MENU);

		imagem.setSize(144, 59);
		imagem.setLocation(138, 20);
		label.add(imagem);

		int x = 5;
		int y = 105;
		for(int i = 0; i < 5; i++) {
			botoes[i].setLocation(x, y);
			y+=40;
		}
		x = 205;
		y = 105;
		for(int i = 5; i < botoes.length; i++) {
			botoes[i].setLocation(x, y);
			y+=40;
		}

		for(JButton botao : botoes) {
			botao.setSize(190, 35);
			botao.setFont(new Font("Tahoma", 10, 10));
			botao.addActionListener(this);
			label.add(botao);
		}


		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		pack();
		setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("convite")) {
			Convidar.getInstancia().setUser(usuario);
			Convidar.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("amigos")) {
			ListaAmigos.getInstancia().setUser(usuario);
			ListaAmigos.getInstancia().setConteudo();
			ListaAmigos.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("compartilhamento")) {
			Consultar.getInstancia().setUser(usuario);
			Consultar.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("editar")) {
			Editar.getInstancia().setUser(usuario);
			Editar.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("excluir")) {
			Excluir.getInstancia().setUser(usuario);
			Excluir.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("localizar")) {
			Localizar.getInstancia().setUser(usuario);
			Localizar.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("email")) {
			Email.getInstancia().setUser(usuario);
			Email.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("sms")) {
			Sms.getInstancia().setUser(usuario);
			Sms.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("aceitarComp")) {
			AceitarConvites.getInstancia().setUsuario(this.usuario.getUsername());
			AceitarConvites.getInstancia().setListaConvites(usuario);
			AceitarConvites.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("logout")) {
			MenuInicial.getInstancia().setVisible(true);
			setVisible(false);
		}
	}

}
