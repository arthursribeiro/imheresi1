package interfaceGrafica;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuInicial extends JFrame implements ActionListener {
	
	//public JFrame frameInicial;
	private JButton botaoCadastro;
	private JButton botaoLogin;
	private JButton botaoInfo;
	private JButton botaoSair;
	static String nome;
	public static final ImageIcon BACKGROUND = criaImagem("qualque.jpg");
	private static MenuInicial instanciaUnica;
	
	private MenuInicial()  {
        botaoCadastro = new JButton("Cadastro");
        botaoLogin = new JButton("Login");
        botaoInfo = new JButton("Informações");
        botaoSair = new JButton("Sair");
        this.setTitle("Janelitxa");
        this.montaJanela();
        
	}
	
    public static MenuInicial getInstancia(){
    	if(instanciaUnica == null) instanciaUnica = new MenuInicial();
    	return instanciaUnica;
    }
    
	private static ImageIcon criaImagem(String imagem) {
		try {
			return new ImageIcon(ImageIO.read(new File(imagem)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void montaJanela() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		
		JLabel label = new JLabel();
		label.setIcon(BACKGROUND); 

		botaoCadastro.setSize(133, 37);
		botaoCadastro.setLocation(213, 145);
		botaoCadastro.setActionCommand("cadastro");
		botaoCadastro.addActionListener(this);
		label.add(botaoCadastro);
		
		botaoLogin.setSize(133, 37);
		botaoLogin.setLocation(213, 190);
		botaoLogin.setActionCommand("login");
		botaoLogin.addActionListener(this);
		label.add(botaoLogin);
		
		botaoInfo.setSize(133, 37);
		botaoInfo.setLocation(213, 245);
		botaoInfo.setActionCommand("info");
		botaoInfo.addActionListener(this);
		label.add(botaoInfo);
		
		botaoSair.setSize(133, 37);
		botaoSair.setLocation(213, 305);
		botaoSair.setActionCommand("sair");
		botaoSair.addActionListener(this);
		label.add(botaoSair);
		
		getContentPane().add(label, BorderLayout.CENTER);
		setBounds(new java.awt.Rectangle(0, 0, 400, 400));
		//frameInicial.setIconImage(BACKGROUND);
		pack();
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(command.equals("cadastro")) {
			Cadastro.getInstancia().limpaDados();
			Cadastro.getInstancia().setVisible(true);
			setVisible(false);
		}
		
		if(command.equals("login")) {
			Login.getInstancia().limpaDados();
			Login.getInstancia().setVisible(true);
			setVisible(false);
		}
		
		if(command.equals("sair")) {
			System.exit(0);
			//TODO Melhorar Isso!!
		}
		
		if(command.equals("info")) {
			Informacoes.getInstancia().setVisible(true);
			setVisible(false);
		}
	}
	
    public static void main(String[] args) {
    	MenuInicial.getInstancia().setVisible(true);
	}
}
