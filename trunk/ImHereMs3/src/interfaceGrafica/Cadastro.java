package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import principal.Sistema;
import util.Usuario;

public class Cadastro extends JFrame {

	private JButton botaoCancelar, botaoOK;
	private JLabel loginLabel, nomeLabel, emailLabel, telefoneLabel,
			senhaLabel, ipLabel;
	private JPasswordField senhaField;
	private JTextField loginField, nomeField, emailField, telefoneField,
			ipField;

	private JButton[] botoes = { botaoCancelar = new JButton("Cancelar"),
			botaoOK = new JButton("OK") };
	private JTextField[] fields = { loginField = new JTextField(),
			nomeField = new JTextField(), emailField = new JTextField(),
			telefoneField = new JTextField(), ipField = new JTextField(),
			senhaField = new JPasswordField() };
	private JLabel[] labels = { loginLabel = new JLabel("Login:"),
			nomeLabel = new JLabel("Nome:"), emailLabel = new JLabel("Email:"),
			telefoneLabel = new JLabel("Telefone:"),
			senhaLabel = new JLabel("Senha:"), ipLabel = new JLabel("IP:") };

	private static Cadastro instanciaUnica;

	private ActionListener cacelarAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MenuInicial.getInstancia().setVisible(true);
			setVisible(false);
		}
	};

	private ActionListener okAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			realizarCadastro();
		}
	};

	private Cadastro() {
		super("Cadastro de usuários");
		comeca();
	}

	public void limpaDados() {
		loginField.setText("");
		nomeField.setText("");
		emailField.setText("");
		telefoneField.setText("");
		ipField.setText("");
		senhaField.setText("");
	}

	public static Cadastro getInstancia() {
		if (instanciaUnica == null)
			instanciaUnica = new Cadastro();
		return instanciaUnica;
	}

	private void realizarCadastro() {
		Sistema sistema = Sistema.getInstancia();

		String login = loginField.getText();
		String nome = nomeField.getText();
		String senha = senhaField.getText();
		String email = emailField.getText();
		String telefone = telefoneField.getText();
		String ip = ipField.getText();

		try {
			sistema.cadastro(login, nome, senha, email, telefone, ip);

			Usuario atualUsuario = sistema.fazerLogin(login, senha, ip);
			MenuUsuario.getInstancia().setUsuario(atualUsuario);
			MenuUsuario.getInstancia().setVisible(true);
			setVisible(false);

		} catch (Exception e) {
			Erro.getInstancia().setErro(e.getMessage());
			Erro.getInstancia().setVisible(true);
			setEnabled(false);
		}

	}

	private void comeca() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(MenuInicial.getInstancia().BACKGROUND);

		int y = 80;
		for (JLabel jlab : labels) {
			jlab.setSize(100, 25);
			jlab.setLocation(10, y);
			jlab.setFont(new Font("Tahoma", 0, 14));
			y += 40;
			label.add(jlab);
		}

		y = 80;
		for (JTextField field : fields) {
			field.setSize(100, 25);
			field.setLocation(100,y);
			y += 40;
			label.add(field);
		}

		botaoCancelar.setSize(100, 30);
		botaoCancelar.setLocation(130, 340);
		label.add(botaoCancelar);

		botaoOK.setSize(80, 30);
		botaoOK.setLocation(10, 340);
		label.add(botaoOK);

		botaoCancelar.addActionListener(cacelarAction);
		botaoOK.addActionListener(okAction);

		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		pack();
		setVisible(true);
	}

}
