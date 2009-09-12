import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User{

	private Collection<PublicInfo> friends;
	private String password;
	private String ip;
	private PublicInfo myPublicInfo;
	private int id;
	
	//Usar sets para setar qualquer tipo de atributo devido aos tratamentos de erros!
	public User(String userName, String password2, int id){
		
	}

	public void addFriend(PublicInfo friend){
		this.friends.add(friend);
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}

	public void setId(int i) {
		// TODO Auto-generated method stub
	}

	
	//Coloquei os tratamentos de erros nos sets aqui pra poder dar uma refatorada! Lembrar de sempre usar os sets pra colocafr alguma coisa
	//inclusive no construtor!
	public void setMail(String email) throws Exception{
		if((email == null) || (email.trim().equals(""))) throw new Exception("E-mail eh um dado obrigatorio.");
		if(!this.validMail(email)) throw new Exception("E-mail invalido.");
	}

	private boolean validMail(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	       
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
	}	

	public void setPassword(String password) throws Exception{
		if((password == null) || (password.trim().equals(""))) throw new Exception("Senha eh um dado obrigatorio.");
		if(password.length() < 6) throw new Exception("Senha deve ter no minimo 6 caracteres.");
		
		this.password = password;
	}
	
	public void setName(String name) throws Exception{
		if((name == null) || (name.trim().equals(""))) throw new Exception("Nome eh um dado obrigatorio.");
		
	}
	
	public void setUserName(String userName) throws Exception{
		if((userName == null) || (userName.trim().equals(""))) throw new Exception("Username eh um dado obrigatorio.");
	}

	public void setPhone(String phone) {
		// TODO Auto-generated method stub
	}
	
		
	public String getPassword(){
		return this.password;
	}
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPhone() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
