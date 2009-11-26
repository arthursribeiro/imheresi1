package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import principal.Sistema;

import util.Usuario;

public class MenuUsuario extends JFrame {

	private JButton botaoEnviarConvite;
	private JButton botaoLogOut;
	private JButton botaoVerAmigos;
	private JButton botaoVerificarModo;
	private JButton botaoEditarModo;
	private JButton botaoExcluirAmigo;
	private JButton botaoVerLocal;
	private JButton botaoEnviarEmail;
	private JButton botaoEnviarSMS;
	private JButton botaoChat;
	private Usuario usuario;

	private static MenuUsuario instanciaUnica;

	private ActionListener logoutAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MenuInicial.getInstancia().setVisible(true);
			setVisible(false);
		}
	};

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
				botaoChat = new JButton("Chat"),
				botaoLogOut = new JButton("LogOut") };
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);

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
			label.add(botao);
		}
		
		botaoLogOut.addActionListener(logoutAction);
		
		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		pack();
		setVisible(true);
	}

	/**
	public static void main(String[] args) {
		Sistema sistema = Sistema.getInstancia();
		Usuario atualUsuario = sistema.fazerLogin("raq", "123456", "198.23.21.2");
		MenuUsuario.getInstancia().setUsuario(atualUsuario);
		MenuUsuario.getInstancia().setVisible(true);
	}*/

}
