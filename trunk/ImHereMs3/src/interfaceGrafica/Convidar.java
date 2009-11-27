package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import principal.Sistema;
import util.Usuario;

public class Convidar extends JFrame implements ActionListener{

	private JTextField emailField;
	private JLabel emailLabel;
	private JButton convidarButton, cancelButton;

	private Usuario user;
	
	private static Convidar instanciaUnica = null;

	private Convidar(){
		super("Convidar Usuario");
		initComponents();
	}

	public static Convidar getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Convidar();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void convidar(){
		String userParaConvidar = this.emailField.getText();

		Sistema sistema = Sistema.getInstancia();
		
		sistema.initMensageiro("email");
		String mensagem = sistema.enviarConvite(user, userParaConvidar);
		
		if (sistema.enviouConvite) {
			Sucesso sucesso = new Sucesso(mensagem);
			setVisible(false);
			this.limpaDados();
			sucesso.setVisible(true);
			
		} else {
			ErroConvidar.getInstancia().setErro("Erro no envio do convite!");
			ErroConvidar.getInstancia().setVisible(true);
			setEnabled(false);
		}
	}
	
	 public void limpaDados(){
	    	emailField.setText("");
	 }
	
	private void initComponents() {
		emailLabel = new JLabel("Email: ");

		emailField = new JTextField();

		emailField.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        convidarButton = new JButton("Convidar");
		cancelButton = new JButton("Cancelar");
		
		convidarButton.setActionCommand("convidar");
		convidarButton.addActionListener(this);
		
		cancelButton.setActionCommand("cancelar");
		cancelButton.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);
		
		convidarButton.setSize(133, 37);
        convidarButton.setLocation(210, 330);
        
        cancelButton.setSize(133, 37);
		cancelButton.setLocation(62, 330);
		
		emailLabel.setSize(100, 30);
		emailLabel.setLocation(20, 36);
		
		emailField.setSize(230, 20);
		emailField.setLocation(120, 36);
		
		label.add(convidarButton);
		label.add(cancelButton);
		label.add(emailLabel);
		label.add(emailField);
		
		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		pack();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("cancelar")) {
			this.limpaDados();
			MenuUsuario.getInstancia().setVisible(true);
			setVisible(false);
		}

		if (command.equals("convidar")) {
			convidar();
		}
	}

}

