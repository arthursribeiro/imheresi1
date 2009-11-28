package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import principal.Sistema;

import util.Usuario;

public class AceitarConvites extends JFrame implements ActionListener {

	private JButton botaoVisivel;
	private JButton botaoOculto;
	private JButton botaoRecusar;
	private JButton botaoVoltar;
	private JScrollPane texto;
	private JLabel conteudo;
	private JLabel nome;
	private JTextField username;

	private String usuarioLogado;
	
	private static AceitarConvites instanciaUnica;

	private AceitarConvites() {
		this.setTitle("Aceitar Convites");
		this.initComponents();

	}

	public static AceitarConvites getInstancia() {
		if (instanciaUnica == null)
			instanciaUnica = new AceitarConvites();
		return instanciaUnica;
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JButton[] botoes = { botaoVisivel = new JButton("Visivel"),
				botaoOculto = new JButton("Oculto"),
				botaoRecusar = new JButton("Recusar"),
				botaoVoltar = new JButton("Voltar") };
		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);

		conteudo = new JLabel("");
		texto = new JScrollPane(conteudo);

		nome = new JLabel("Username: ");
		username = new JTextField();

		botaoVisivel.setActionCommand("visivel");
		botaoVisivel.addActionListener(this);

		botaoOculto.setActionCommand("oculto");
		botaoOculto.addActionListener(this);

		botaoRecusar.setActionCommand("recusar");
		botaoRecusar.addActionListener(this);

		botaoVoltar.setActionCommand("voltar");
		botaoVoltar.addActionListener(this);

		int x = 10;

		for (JButton botao : botoes) {
			botao.setSize(85, 37);
			botao.setLocation(x, 350);
			label.add(botao);
			x += 100;
		}

		username.setSize(100, 30);
		username.setLocation(150, 250);

		nome.setSize(100, 30);
		nome.setLocation(70, 250);

		texto.setLocation(20,15);
		texto.setSize(350, 200);

		label.add(texto);
		label.add(username);
		label.add(nome);

		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if(!username.getText().equals("")){
			Sistema sistema = Sistema.getInstancia();
			String u = username.getText();
			if (command.equals("visivel")) {
				sistema.confirmarCompartilhamento(this.usuarioLogado,u, 2);
				MenuUsuario.getInstancia().setVisible(true);
				setVisible(false);
			}

			if (command.equals("oculto")) {
				sistema.confirmarCompartilhamento(this.usuarioLogado,u, 1);
				MenuUsuario.getInstancia().setVisible(true);
				setVisible(false);
			}

			if (command.equals("recusar")) {
				sistema.recusarCompartilhamento(this.usuarioLogado,u);
				MenuUsuario.getInstancia().setVisible(true);
				setVisible(false);
			}
		}
		if (command.equals("voltar")) {
			MenuUsuario.getInstancia().setVisible(true);
			setVisible(false);
		}
	}

	public void setListaConvites(Usuario user){
		String saida = "<html>";
		Sistema sistema = Sistema.getInstancia();
		if(user.temConvites()){
			Iterator<String> iterator = user.getConvites();
			Usuario amigo;

			while(iterator.hasNext()){
				amigo = sistema.procurePorLogin(iterator.next());
				saida += amigo.getUsername() + "<br>";
			}
		} else {
			saida += "Nenhum convite pendente.";
		}

		saida += "</p></html>";
		this.conteudo.setText(saida);
	}

	public void setUsuario(String us){
		this.usuarioLogado = us;
	}
	
}
