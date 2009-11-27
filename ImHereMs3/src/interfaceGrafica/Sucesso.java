package interfaceGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class Sucesso extends JFrame {

    private JButton botaoOK;
    private JLabel sucessoLabel;
    private String sucesso;
	
	private ActionListener okAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			MenuUsuario.getInstancia().setVisible(true);
		}
	};
	
    public Sucesso(String sucesso) {
    	super("Sucesso!");
    	this.sucesso = sucesso;
        initComponents();
    }

    
    private void initComponents() {

        sucessoLabel = new JLabel(sucesso);
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
                        .addComponent(sucessoLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(botaoOK)))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(sucessoLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(botaoOK)
                .addGap(24, 24, 24))
        );

        pack();
    }


}
