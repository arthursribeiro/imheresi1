package interfaceGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class Erro extends JFrame {

    private JButton botaoOK;
    private JLabel erroLabel;
	private static Erro instanciaUnica;
    
	private ActionListener okAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Cadastro.getInstancia().setEnabled(true);
			Login.getInstancia().setEnabled(true);
			setVisible(false);
		}
	};
	
    private Erro() {
    	super("Erro");
        initComponents();
    }

    public static Erro getInstancia(){
    	if(instanciaUnica == null) instanciaUnica = new Erro();
    	return instanciaUnica;
    }

    public void setErro(String mensagemErro){
    	erroLabel.setText(mensagemErro);
    }
    
    private void initComponents() {

        erroLabel = new JLabel("");
        botaoOK = new JButton("OK");

        botaoOK.addActionListener(okAction);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setResizable(false);
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(erroLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(botaoOK)))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(erroLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(botaoOK)
                .addGap(24, 24, 24))
        );

        pack();
    }


}
