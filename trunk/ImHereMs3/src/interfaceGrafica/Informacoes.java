package interfaceGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Informacoes extends JFrame {

    private JButton botao;
    private JLabel texto;
    private JLabel figura;
	
    private final String textoInfo = "<html>I'm Here é um sistema que permite compartilhamento de localização de pessoas<br> Os usuários só poderão visualizar a informação de localização uns dos outros se<br> assim desejarem. Para que isso seja possível um convite de compartilhamento deve<br> ser enviado a um usuário, o qual define se exibe ou oculta sua localização. Nesse<br> cenário, os usuários podem trocar mensagens entre si, seja um e-mail, um SMS ou<br> até conversar por chat.</p></html>";
    private static Informacoes instanciaUnica;
    
	private ActionListener voltar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MenuInicial.getInstancia().setVisible(true);
			setVisible(false);
		}
	};
    
    private Informacoes() {
    	super("Informações sobre o I'm Here");
        initComponents();
    }
    
    public static Informacoes getInstancia(){
    	if(instanciaUnica == null) instanciaUnica = new Informacoes();
    	return instanciaUnica;
    }

    private void initComponents() {

        botao = new JButton("Voltar");
        texto = new JLabel(textoInfo);
        figura = new JLabel(new ImageIcon("8.jpg"));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        botao.addActionListener(voltar);        
        GroupLayout layout = new GroupLayout(getContentPane());
        setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(figura, GroupLayout.PREFERRED_SIZE , 358, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(texto, 480, 480, 480)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(botao, 100, 100, 100)
                .addGap(100, 100, 100))

        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(figura,120, 120, 120)
                .addGap(33, 33, 33)
                .addComponent(texto, 100, 100, 100)
                .addGap(27, 27, 27)
                .addComponent(botao, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        pack();
    }
    
}
