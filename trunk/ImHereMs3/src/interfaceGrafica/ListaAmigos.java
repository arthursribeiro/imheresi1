package interfaceGrafica;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import util.Amigo;
import util.Usuario;

public class ListaAmigos extends JFrame{

	private JButton botao;
    private JScrollPane texto;
    private JLabel figura;
    
    private String txt = "<html> Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br>Hi.<br> </p></html>";
    
    private ActionListener voltar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MenuUsuario.getInstancia().setVisible(true);
			setVisible(false);
		}
	};
	
	private Usuario usuario;
	private static ListaAmigos instanciaUnica = null;
	
	private ListaAmigos(){
		super("Lista de amigos");
        initComponents();
	}
	
	public static ListaAmigos getInstancia(){
		if(instanciaUnica == null) 
			instanciaUnica = new ListaAmigos();
		
		return instanciaUnica;
	}
	
	public void setUser(Usuario user){
		this.usuario = user;
	}
	
	private void initComponents() {

        botao = new JButton("Voltar");
        texto = new JScrollPane(new JLabel(txt));
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

	//ver esse metodo
	private String getStringAmigos() {
		String saida = "<html>";

		if(this.usuario.getListaAmigos().size() != 0){
			Iterator<Amigo> it = this.usuario.getListaAmigos().iterator();
			
			while(it.hasNext()){
				String usuario = it.next().toString();
				saida += usuario + "<br>";
			}
			
		} else
			saida += "Nenhum amigo adicionado.";
		
		
		
		saida += "</p></html>";
		
		return saida;
	}
	
	
	

}
