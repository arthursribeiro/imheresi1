package Project;
import java.util.ArrayList;


public class SystemFacade {
	
	private System mySystem;
	private ArrayList<User> createdUsers;
	
	public void zerarSistema(){
		mySystem = new System();
		mySystem.resetBD();
		createdUsers = new ArrayList<User>();
	}
	
	//Encerra o sistema, gravando log e informacoes dos usuarios
	public void encerrarSistema(){
		for (User user : createdUsers) {
			mySystem.saveUser(user);
		}
	}
	
	public void criarUsuario(String userName, String nome, String email, String senha, String telefone) throws Exception{
			User user = mySystem.creatUser(userName, senha, email, nome, telefone);
			createdUsers.add(user);
	}
	
	public int getUsuarioPorNome(String nome, int indice) throws Exception{
		User user = mySystem.getUserbyName(nome, indice);
		return user.getId();
	}
	
	public String getAtributoUsuario(int id, String atributo) throws Exception{
		User user = mySystem.getUserById(id); // lanca exececao de nao encontrar o usuario com esse id
		
		if(atributo.equalsIgnoreCase("nome")) return user.getName();
		if(atributo.equalsIgnoreCase("email")) return user.getMail();
		if(atributo.equalsIgnoreCase("userName")) return user.getUserName();
		if(atributo.equalsIgnoreCase("telefone")) return user.getPhone();
		
		return null;
	}
	
	public void atualizarUsuario(int id, String atributo, String valor) throws Exception{
		
		//Os updates utilizam os sets que ja lancam as excecoes.. refatoramento das exececoes!
		if(atributo.equalsIgnoreCase("nome")) mySystem.updateName(id, valor);
		if(atributo.equalsIgnoreCase("email")) mySystem.updateMail(id, valor);
		if(atributo.equalsIgnoreCase("telefone")) mySystem.updatePhone(id, valor);
		if(atributo.equalsIgnoreCase("userName")) throw new Exception("Nao eh permitido alterar o username.");
	}
	
	public void removerUsuario(int id) throws Exception{
		mySystem.removeUser(mySystem.getUserById(id));
	}
	
	
	
	
	
	
	
	
	

}
