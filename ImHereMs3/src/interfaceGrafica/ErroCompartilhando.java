package interfaceGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class ErroCompartilhando extends JFrame {

    private JButton botaoOK;
    private JLabel erroLabel;
	private static ErroCompartilhando instanciaUnica;
	private int MODO;
    
	private ActionListener okAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(MODO == 0)
			    Consultar.getInstancia().setEnabled(true);
			else
				Editar.getInstancia().setEnabled(true);
			setVisible(false);
		}
	};
	
    private ErroCompartilhando(int MODO) {
    	super("Erro");
    	this.MODO = MODO; 
        initComponents();
    }

    public static ErroCompartilhando getInstancia(int MODO){
    	if(instanciaUnica == null) instanciaUnica = new ErroCompartilhando(MODO);
    	instanciaUnica.MODO = MODO;
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
