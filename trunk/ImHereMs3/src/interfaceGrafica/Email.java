package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import principal.Sistema;
import util.Usuario;

public class Email extends JFrame implements ActionListener{

	private JTextField destinationMail,subject;
	private JTextArea mensagem;
	private JLabel toLabel, subjectLabel, MessageLabel;
	private JButton sendButton, cancelButton;

	private Usuario user;
	Sucesso sucesso = new Sucesso("Email enviado com sucesso!");

	private static Email instanciaUnica = null;

	private Email(){
		super("Enviar email");
		initComponents();
	}

	public static Email getInstancia(){
		if(instanciaUnica == null)
			instanciaUnica = new Email();
		
		return instanciaUnica;
	}

	public void setUser(Usuario user){
		this.user = user;
	}
	
	private void send(){
		String email = this.destinationMail.getText();
		String subject2 = this.subject.getText();
		String mensagem2 = this.mensagem.getText();
		
		Sistema sistema = Sistema.getInstancia();
		sistema.enviarEmail(user.getUsername(), email, subject2, mensagem2);
	}
	
	 public void limpaDados(){
	    	destinationMail.setText("");
	    	subject.setText("");
	    	mensagem.setText("");
	 }
	
	private void initComponents() {
		toLabel = new JLabel("Destinatario: ");
		subjectLabel = new JLabel("Assunto: ");
		MessageLabel = new JLabel("Mensagem: ");

		destinationMail = new JTextField();
		subject = new JTextField();

		mensagem = new JTextArea(20, 20);
		mensagem.setLineWrap(true);
		
		JScrollPane txt = new JScrollPane(mensagem);
		
		destinationMail.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        subject.setFont(new java.awt.Font("Tahoma", 0, 14));
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
		
		subjectLabel.setSize(100, 30);
		subjectLabel.setLocation(20, 66);
		
		MessageLabel.setSize(100, 30);
		MessageLabel.setLocation(20, 96);
		
		//
		
		destinationMail.setSize(230, 20);
		destinationMail.setLocation(120, 36);
		
		subject.setSize(230, 20);
		subject.setLocation(120, 66);
		
		txt.setSize(230, 180);
		txt.setLocation(120, 96);
		
		label.add(sendButton);
		label.add(cancelButton);
		label.add(toLabel);
		label.add(subjectLabel);
		label.add(MessageLabel);
		label.add(destinationMail);
		label.add(subject);
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

