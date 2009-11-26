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
		super("Menu do Ususário");
		begin();
	}

	public void setUsuario(Usuario u) {
		usuario = u;
	}

	public static MenuUsuario getInstancia() {
		if (instanciaUnica == null)
			instanciaUnica = new MenuUsuario();
		return instanciaUnica;
	}

	private void begin() {
		
		JButton[] botoes = {
		botaoEnviarConvite = new JButton("Enviar Convite"), //
		botaoVerAmigos = new JButton("Ver Amigos"), //
		botaoVerificarModo = new JButton("Modo de Compartilhamento"), //
		botaoEditarModo = new JButton("Editar Modo de Compartilhamento"), //
		botaoExcluirAmigo = new JButton("Excluir Amigo"),
		botaoVerLocal = new JButton("Localização de Amigos"),
		botaoEnviarEmail = new JButton("Enviar Email"),
		botaoEnviarSMS = new JButton("Enviar SMS"),
		botaoChat = new JButton("Chat"),
		botaoLogOut = new JButton("LogOut") };

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(MenuInicial.getInstancia().BACKGROUND);

		int x = 5;
		int y = 105;
		for(int i = 0; i < botoes.length; i++) {
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

	private void initComponents() {

		botaoEnviarConvite = new JButton("Enviar Convite");
		botaoVerAmigos = new JButton("Ver Amigos");
		botaoVerificarModo = new JButton("Verificar modo de Compartilhamento");
		botaoEditarModo = new JButton("Editar modo de Compartilhamento");
		botaoExcluirAmigo = new JButton("Excluir Amigo");
		botaoVerLocal = new JButton("Ver localização de Amigos");
		botaoEnviarEmail = new JButton("Enviar Email");
		botaoEnviarSMS = new JButton("Enviar SMS");
		botaoChat = new JButton("Chat");
		botaoLogOut = new JButton("LogOut");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setResizable(false);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												botaoEnviarConvite)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												35,
																												Short.MAX_VALUE)
																										.addComponent(
																												botaoVerAmigos)
																										.addGap(
																												33,
																												33,
																												33)
																										.addComponent(
																												botaoVerLocal))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												botaoEditarModo)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												botaoEnviarSMS)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												botaoExcluirAmigo))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												botaoEnviarEmail)
																										.addGap(
																												31,
																												31,
																												31)
																										.addComponent(
																												botaoVerificarModo)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												31,
																												Short.MAX_VALUE)
																										.addComponent(
																												botaoChat))))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				200,
																				200,
																				200)
																		.addComponent(
																				botaoLogOut)))
										.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								botaoEnviarConvite).addComponent(botaoVerLocal)
								.addComponent(botaoVerAmigos)).addGap(18, 18,
						18).addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								botaoEditarModo)
								.addComponent(botaoExcluirAmigo).addComponent(
										botaoEnviarSMS)).addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(botaoEnviarEmail)
										.addComponent(botaoChat).addComponent(
												botaoVerificarModo)).addGap(18,
								18, 18).addComponent(botaoLogOut)
						.addContainerGap(21, Short.MAX_VALUE)));

		pack();
	}

	/**
	public static void main(String[] args) {
		Sistema sistema = Sistema.getInstancia();
		Usuario atualUsuario = sistema.fazerLogin("raq", "123456", "198.23.21.2");
		MenuUsuario.getInstancia().setUsuario(atualUsuario);
		MenuUsuario.getInstancia().setVisible(true);
	}*/

}
