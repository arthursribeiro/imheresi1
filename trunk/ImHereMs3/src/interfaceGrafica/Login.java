package interfaceGrafica;

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


public class Login extends JFrame {

    private JButton botaoCacelar,botaoOK;
    private JLabel loginLabel,senhaLabel,ipLabel;
    private JPasswordField senhaField;
    private JTextField loginField,ipField;
	
    private static Login instanciaUnica;
    
	private ActionListener cacelarAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MenuInicial.getInstancia().setVisible(true);
			setVisible(false);
		}
	};
    
	private ActionListener okAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			realizarLogin();
		}
	};
    
	private Login() {
		super("Login");
        initComponents();
    }

    public void limpaDados(){
    	loginField.setText("");
    	ipField.setText("");
    	senhaField.setText("");
    }
	
	public static Login getInstancia(){
		if(instanciaUnica == null) instanciaUnica = new Login();
		return instanciaUnica;
	}

	private void realizarLogin(){
    	Sistema sistema = Sistema.getInstancia();
    	
    	String login = loginField.getText();
    	String senha = senhaField.getText();
    	String ip = ipField.getText();
		
    	try{   		
        	Usuario atualUsuario = sistema.fazerLogin(login, senha, ip);
        	MenuUsuario.getInstancia().setUsuario(atualUsuario);
        	MenuUsuario.getInstancia().setVisible(true);
        	setVisible(false);  
    	} catch (Exception e) {
			ErroLogin.getInstancia().setErro(e.getMessage());
			ErroLogin.getInstancia().setVisible(true);
			setEnabled(false);
		}
	}
	
    private void initComponents() {

        loginLabel = new JLabel("Login:");
        senhaLabel = new JLabel("Senha:");
        ipLabel = new JLabel("IP:");
        loginField = new JTextField();
        ipField = new JTextField();
        senhaField = new JPasswordField();
        botaoCacelar = new JButton("Cancelar");
        botaoOK = new JButton("OK");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        botaoCacelar.addActionListener(cacelarAction);
        botaoOK.addActionListener(okAction);
        
        loginLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        senhaLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        ipLabel.setFont(new java.awt.Font("Tahoma", 0, 14));

        setResizable(false);
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginLabel)
                        .addGap(18, 18, 18)
                        .addComponent(loginField, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(senhaLabel)
                        .addGap(18, 18, 18)
                        .addComponent(senhaField, 0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ipLabel)
                        .addGap(18, 18, 18)
                        .addComponent(ipField, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(botaoCacelar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(botaoOK)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(loginLabel)
                    .addComponent(loginField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(senhaLabel)
                    .addComponent(senhaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoOK)
                    .addComponent(botaoCacelar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }

}
