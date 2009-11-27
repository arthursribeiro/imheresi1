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

public class Localizar extends JFrame implements ActionListener{

	private JTextField userField;
	private JLabel userLabel;
	private JButton localizarButton, cancelButton;

	private Usuario user;
	
	private static Localizar instanciaUnica = null;

	private Localizar(){
		super("Localizar Usuario");
		initComponents();
	}

	public static Localizar getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Localizar();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void localizar(){
		String userParaLocalizar = this.userField.getText();

		Sistema sistema = Sistema.getInstancia();
		
		try{
			String localizacao = sistema.getLocalizacaoAmigo(this.user.getUsername(), userParaLocalizar);
			Sucesso sucesso = new Sucesso(localizacao);
			
			setVisible(false);
			this.limpaDados();
			sucesso.setVisible(true);
		} catch (Exception ex){
			ErroLocalizacao.getInstancia().setErro(ex.getMessage());
			ErroLocalizacao.getInstancia().setVisible(true);
			setEnabled(false);
		}
	}
	
	 public void limpaDados(){
	    	userField.setText("");
	 }
	
	private void initComponents() {
		userLabel = new JLabel("Usuario: ");

		userField = new JTextField();

		userField.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        localizarButton = new JButton("Localizar");
		cancelButton = new JButton("Cancelar");
		
		localizarButton.setActionCommand("localizar");
		localizarButton.addActionListener(this);
		
		cancelButton.setActionCommand("cancelar");
		cancelButton.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);
		
		localizarButton.setSize(133, 37);
        localizarButton.setLocation(210, 330);
        
        cancelButton.setSize(133, 37);
		cancelButton.setLocation(62, 330);
		
		userLabel.setSize(100, 30);
		userLabel.setLocation(20, 36);
		
		userField.setSize(230, 20);
		userField.setLocation(120, 36);
		
		label.add(localizarButton);
		label.add(cancelButton);
		label.add(userLabel);
		label.add(userField);
		
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

		if (command.equals("localizar")) {
			localizar();
		}
	}

}

