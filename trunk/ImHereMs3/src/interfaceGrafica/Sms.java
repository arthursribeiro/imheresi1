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

public class Sms extends JFrame implements ActionListener{

	private JTextField destinationFone;
	private JTextArea mensagem;
	private JLabel toLabel, MessageLabel;
	private JButton sendButton, cancelButton;

	private Usuario user;
	Sucesso sucesso = new Sucesso("SMS enviado com sucesso!");

	private static Sms instanciaUnica = null;

	private Sms(){
		super("Enviar SMS");
		initComponents();
	}

	public static Sms getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Sms();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void send(){
		String fone = this.destinationFone.getText();
		String mensagem2 = this.mensagem.getText();
		
		Sistema sistema = Sistema.getInstancia();
		sistema.enviarSMS(this.user.getUsername(), fone, mensagem2);
	}
	
	 public void limpaDados(){
	    	destinationFone.setText("");
	    	mensagem.setText("");
	 }
	
	private void initComponents() {
		toLabel = new JLabel("Destinatario: ");
		MessageLabel = new JLabel("Mensagem: ");

		destinationFone = new JTextField();

		mensagem = new JTextArea(20, 20);
		mensagem.setLineWrap(true);
		
		JScrollPane txt = new JScrollPane(mensagem);
		
		destinationFone.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        mensagem.setFont(new java.awt.Font("Tahoma", 0, 14));
        
        sendButton = new JButton("Enviar");
		cancelButton = new JButton("Cancelar");
		
		sendButton.setActionCommand("send");
		sendButton.addActionListener(this);
		
		cancelButton.setActionCommand("cancelar");
		cancelButton.addActionListener(this);
		
		
		
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel();
		label.setIcon(Images.BACKGROUND);
		
		sendButton.setSize(133, 37);
        sendButton.setLocation(210, 330);
        
        cancelButton.setSize(133, 37);
		cancelButton.setLocation(62, 330);
		
		toLabel.setSize(100, 30);
		toLabel.setLocation(20, 36);
		
		MessageLabel.setSize(100, 30);
		MessageLabel.setLocation(20, 66);
		
		//
		
		destinationFone.setSize(230, 20);
		destinationFone.setLocation(120, 36);
		
		txt.setSize(230, 180);
		txt.setLocation(120, 66);
		
		label.add(sendButton);
		label.add(cancelButton);
		label.add(toLabel);
		label.add(MessageLabel);
		label.add(destinationFone);
		label.add(txt);
		
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

		if (command.equals("send")) {
			send();

			this.limpaDados();
			setVisible(false);
			
			sucesso.setVisible(true);
		}
	}

}

