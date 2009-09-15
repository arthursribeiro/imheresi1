package AcceptanceTests;

import Project.MainSystem;

public class Us7_2_Facade {
	
	private MainSystem mySystem;
	
	public String login(String userName, String senha, String ip) throws Exception {
		return this.mySystem.logIn(userName, senha, ip);
	}
	
	public void initMensageiro(String sistema){
		//Nao sei o que isso faz...
	}
		
	public String enviarSMS(String de, String para, String msg) throws Exception{	
		mySystem.sendSMS(de, para, msg);
		return "SMS enviado com sucesso.";
	}
	
	public void logout(String id) throws Exception{
		mySystem.logOut(id);
	}
	
}
