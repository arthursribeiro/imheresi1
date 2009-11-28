package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import principal.Sistema;
import util.Usuario;

public class Editar extends JFrame implements ActionListener{

	private JTextField userField;
	private JLabel userLabel;
	private JLabel compartilharLabel;
	private JButton editarButton, cancelButton;
	private JCheckBox compartilhamento;

	private Usuario user;
	
	private static Editar instanciaUnica = null;

	private Editar(){
		super("Editar visibilidade");
		initComponents();
	}

	public static Editar getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Editar();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void editar(){
		String userParaConsultar = this.userField.getText();
		int novoCompartilhamento;
		
		Sistema sistema = Sistema.getInstancia();
		Sucesso sucesso = null;
		
		if(compartilhamento.isSelected()) {
			novoCompartilhamento = 2;
		} else {
			novoCompartilhamento = 1;
		}
		
		try{			
			if(sistema.setCompartilhamento(user.getUsername(), userParaConsultar,
					novoCompartilhamento)) {
				if(novoCompartilhamento == 2)
				    sucesso = new Sucesso("Voce está agora compartilhando sua posição com " + userParaConsultar + "!");
				else
					sucesso = new Sucesso("Voce está agora omitindo sua posição para " + userParaConsultar + "!");
			}
			setVisible(false);
			this.limpaDados();
			sucesso.setVisible(true);
			
		} catch (Exception ex){
			ErroCompartilhando.getInstancia(1).setErro("Compartilhamento nao foi modificado.");
			ErroCompartilhando.getInstancia(1).setVisible(true);
			setEnabled(false);
		}
	}
	
	 public void limpaDados(){
	    	userField.setText("");
	 }
	
	private void initComponents() {
		userLabel = new JLabel("Usuario: ");
		compartilharLabel = new JLabel("Compartilhar: ");

		userField = new JTextField();

		userField.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        compartilhamento = new JCheckBox();
		
		editarButton = new JButton("Editar");
		cancelButton = new JButton("Cancelar");
		
		editarButton.setActionCommand("editar");
		editarButton.addActionListener(this);
		
		cancelButton.setActionCommand("cancelar");
		cancelButton.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);
		
		editarButton.setSize(133, 37);
		editarButton.setLocation(210, 330);
        
        cancelButton.setSize(133, 37);
		cancelButton.setLocation(62, 330);
		
		userLabel.setSize(100, 30);
		userLabel.setLocation(20, 36);
		
		compartilharLabel.setSize(130, 30);
		compartilharLabel.setLocation(20, 66);
		
		compartilhamento.setSize(17, 15);
		compartilhamento.setBackground(label.getBackground());
		compartilhamento.setLocation(120, 70);
		compartilhamento.setVisible(true);
		
		userField.setSize(230, 20);
		userField.setLocation(120, 36);
		
		label.add(editarButton);
		label.add(cancelButton);
		label.add(userLabel);
		label.add(compartilharLabel);
		label.add(compartilhamento);
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

		if (command.equals("editar")) {
			editar();
		}
	}

}

