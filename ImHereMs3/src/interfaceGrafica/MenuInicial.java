package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuInicial extends JFrame implements ActionListener {

	private JButton botaoCadastro;
	private JButton botaoLogin;
	private JButton botaoInfo;
	private JButton botaoSair;
	static String nome;

	private static MenuInicial instanciaUnica;

	private MenuInicial() {
		this.setTitle("I'm Here!");
		this.initComponents();

	}

	public static MenuInicial getInstancia() {
		if (instanciaUnica == null)
			instanciaUnica = new MenuInicial();
		return instanciaUnica;
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		JButton[] botoes = { botaoCadastro = new JButton("Cadastro"),
				botaoLogin = new JButton("Login"),
				botaoInfo = new JButton("Informações"),
				botaoSair = new JButton("Sair") };
		JLabel label = new JLabel();
		label.setIcon(Images.TELA_INICIAL);

		botaoCadastro.setActionCommand("cadastro");
		botaoCadastro.addActionListener(this);

		botaoLogin.setActionCommand("login");
		botaoLogin.addActionListener(this);

		botaoInfo.setActionCommand("info");
		botaoInfo.addActionListener(this);

		botaoSair.setActionCommand("sair");
		botaoSair.addActionListener(this);

		int y = 145;
		for (JButton botao : botoes) {
			botao.setSize(133, 37);
			botao.setLocation(230, y);
			label.add(botao);
			y += 45;
		}

		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("cadastro")) {
			Cadastro.getInstancia().limpaDados();
			Cadastro.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("login")) {
			Login.getInstancia().limpaDados();
			Login.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("sair")) {
			System.exit(0);
			// TODO Melhorar Isso!!
		}

		if (command.equals("info")) {
			Informacoes.getInstancia().setVisible(true);
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		MenuInicial.getInstancia().setVisible(true);
	}
}
