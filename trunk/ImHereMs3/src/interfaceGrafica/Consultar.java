package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import principal.Sistema;
import util.Usuario;

public class Consultar extends JFrame implements ActionListener{

	private JTextField userField;
	private JLabel userLabel;
	private JButton consultarButton, cancelButton;

	private Usuario user;
	
	private static Consultar instanciaUnica = null;

	private Consultar(){
		super("Consultar visibilidade");
		initComponents();
	}

	public static Consultar getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Consultar();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void consultar(){
		String userParaConsultar = this.userField.getText();

		Sistema sistema = Sistema.getInstancia();
		Sucesso sucesso;
		
		try{
			
			if(sistema.possoVerLocalizacao(this.user.getUsername(), userParaConsultar)){
				sucesso = new Sucesso("O usuario " + userParaConsultar + " esta compartilhando a posicao dele com voce!");
			} else {
				 sucesso = new Sucesso("O usuario " + userParaConsultar + " nao esta compartilhando a posicao dele com voce!");
			}
			
			setVisible(false);
			this.limpaDados();
			sucesso.setVisible(true);
			
		} catch (Exception ex){
			
			ErroConsultar.getInstancia().setErro(ex.getMessage());
			ErroConsultar.getInstancia().setVisible(true);
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
        
        consultarButton = new JButton("Consultar");
		cancelButton = new JButton("Cancelar");
		
		consultarButton.setActionCommand("consultar");
		consultarButton.addActionListener(this);
		
		cancelButton.setActionCommand("cancelar");
		cancelButton.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);
		
		consultarButton.setSize(133, 37);
		consultarButton.setLocation(210, 330);
        
        cancelButton.setSize(133, 37);
		cancelButton.setLocation(62, 330);
		
		userLabel.setSize(100, 30);
		userLabel.setLocation(20, 36);
		
		userField.setSize(230, 20);
		userField.setLocation(120, 36);
		
		label.add(consultarButton);
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

		if (command.equals("consultar")) {
			consultar();
		}
	}

}

