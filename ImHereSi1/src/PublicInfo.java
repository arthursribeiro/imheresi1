
public class PublicInfo {

	private String name;
	private String login;
	private Position position;
	private boolean visible;
	
	public PublicInfo(String name, String login, Position position, boolean visible){
		this.name = name;
		this.login = login;
		this.position = position;
		this.visible = visible;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
}
