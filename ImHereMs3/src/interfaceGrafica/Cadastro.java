package interfaceGrafica;

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

    private JButton botaoCancelar,botaoOK;
    private JLabel loginLabel,nomeLabel,emailLabel,telefoneLabel,senhaLabel,ipLabel;
    private JPasswordField senhaField;
    private JTextField loginField,nomeField,emailField,telefoneField,ipField;
	
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
        initComponents();
    }

    public void limpaDados(){
    	loginField.setText("");
    	nomeField.setText("");
    	emailField.setText("");
    	telefoneField.setText("");
    	ipField.setText("");
    	senhaField.setText("");
    }
    
    public static Cadastro getInstancia(){
    	if(instanciaUnica == null) instanciaUnica = new Cadastro();
    	return instanciaUnica;
    }
    
    private void realizarCadastro(){
    	Sistema sistema = Sistema.getInstancia();
    	
    	String login = loginField.getText();
    	String nome = nomeField.getText();
    	String senha = senhaField.getText();
    	String email = emailField.getText();
    	String telefone = telefoneField.getText();
    	String ip = ipField.getText();
    	
    	try{
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
    
    private void initComponents() {
        loginLabel = new JLabel("Login:");
        nomeLabel = new JLabel("Nome:");
        emailLabel = new JLabel("Email:");
        telefoneLabel = new JLabel("Telefone:");
        senhaLabel = new JLabel("Senha:");
        ipLabel = new JLabel("IP:");
        senhaField = new JPasswordField();
        loginField = new JTextField();
        nomeField = new JTextField();
        emailField = new JTextField();
        telefoneField = new JTextField();
        ipField = new JTextField();
        botaoCancelar = new JButton("Cancelar");
        botaoOK = new JButton("OK");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loginLabel.setFont(new Font("Tahoma", 0, 14)); 
        nomeLabel.setFont(new Font("Tahoma", 0, 14)); 
        emailLabel.setFont(new Font("Tahoma", 0, 14)); 
        telefoneLabel.setFont(new Font("Tahoma", 0, 14));
        senhaLabel.setFont(new Font("Tahoma", 0, 14));
        ipLabel.setFont(new Font("Tahoma", 0, 14)); 

        botaoCancelar.addActionListener(cacelarAction);
        botaoOK.addActionListener(okAction);
        
        setResizable(false);
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(ipLabel)
                                .addGap(18, 18, 18)
                                .addComponent(ipField))
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(senhaLabel)
                                .addGap(18, 18, 18)
                                .addComponent(senhaField, 0, 0, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(telefoneLabel)
                                .addGap(18, 18, 18)
                                .addComponent(telefoneField))
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(loginLabel)
                                .addGap(18, 18, 18)
                                .addComponent(loginField))
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(nomeLabel)
                                .addGap(18, 18, 18)
                                .addComponent(nomeField))
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(emailLabel)
                                .addGap(18, 18, 18)
                                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(40, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoCancelar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(botaoOK)
                        .addContainerGap(35, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(loginLabel)
                    .addComponent(loginField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeLabel)
                    .addComponent(nomeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(telefoneLabel)
                    .addComponent(telefoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(senhaLabel)
                    .addComponent(senhaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoOK)
                    .addComponent(botaoCancelar))
                .addContainerGap())
        );

        pack();
    }

}
