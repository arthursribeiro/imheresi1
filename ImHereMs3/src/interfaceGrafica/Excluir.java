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

public class Excluir extends JFrame implements ActionListener{

	private JTextField userField;
	private JLabel userLabel;
	private JButton excluirButton, cancelButton;

	private Usuario user;
	Sucesso sucesso = new Sucesso("Usuario excluido com sucesso!");

	private static Excluir instanciaUnica = null;

	private Excluir(){
		super("Excluir Usuario");
		initComponents();
	}

	public static Excluir getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Excluir();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void excluir(){
		String userParaExcluir = this.userField.getText();

		Sistema sistema = Sistema.getInstancia();
		
		try{
			sistema.removerAmigo(this.user.getUsername(), userParaExcluir);
			setVisible(false);
			this.limpaDados();
			sucesso.setVisible(true);
		} catch (Exception ex){
			ErroExcluir.getInstancia().setErro(ex.getMessage());
			ErroExcluir.getInstancia().setVisible(true);
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
        
        excluirButton = new JButton("Excluir");
		cancelButton = new JButton("Cancelar");
		
		excluirButton.setActionCommand("excluir");
		excluirButton.addActionListener(this);
		
		cancelButton.setActionCommand("cancelar");
		cancelButton.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);
		
		excluirButton.setSize(133, 37);
        excluirButton.setLocation(210, 330);
        
        cancelButton.setSize(133, 37);
		cancelButton.setLocation(62, 330);
		
		userLabel.setSize(100, 30);
		userLabel.setLocation(20, 36);
		
		userField.setSize(230, 20);
		userField.setLocation(120, 36);
		
		label.add(excluirButton);
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

		if (command.equals("excluir")) {
			excluir();
		}
	}

}

