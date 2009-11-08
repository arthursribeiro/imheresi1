package interfaceGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;



public class MenuInicial extends JFrame {

	private ActionListener infoAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Informacoes.getInstancia().setVisible(true);
			setVisible(false);
		}
	};
	
	private ActionListener sairAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			//TODO Melhorar Isso!!
		}
	};
	
    private JButton botaoCadastro;
    private JButton botaoLogin;
    private JButton botaoInfo;
    private JButton botaoSair;
	
    private static MenuInicial menu;
    
    private MenuInicial() {
    	super("Menu Inicial");
        initComponents();
    }

    public static MenuInicial getInstancia(){
    	if(menu == null) menu = new MenuInicial();
    	return menu;
    }
        
    private void initComponents() {

        botaoCadastro = new JButton("Cadastro");
        botaoLogin = new JButton("Login");
        botaoInfo = new JButton("Informações");
        botaoSair = new JButton("Sair");

        botaoInfo.addActionListener(infoAction);
        botaoSair.addActionListener(sairAction);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(28, Short.MAX_VALUE)
                        .addComponent(botaoCadastro)
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(botaoInfo)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(botaoSair)
                    .addComponent(botaoLogin))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoLogin)
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(botaoInfo)
                            .addComponent(botaoSair)))
                    .addComponent(botaoCadastro))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }
    
    public static void main(String[] args) {
    	MenuInicial.getInstancia().setVisible(true);
	}
    

}
