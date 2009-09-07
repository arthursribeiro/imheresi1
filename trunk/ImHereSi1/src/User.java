import java.util.Collection;


public class User {

	private Collection<PublicInfo> friends;
	private String password;
	private Position myPosition;
	private String email;
	private String telephoneNumber;
	private PublicInfo myPublicInfo;
	
	public User(){
		
	}
	
	public void updatePosition(int ip){
//		this.myPosition.setPosition();
	}
	
	public void updateName(String newName){
		this.myPublicInfo.setName(newName);
	}
	
	public void updateTelephoneNumber(String newTelephoneNumber){
		this.telephoneNumber = newTelephoneNumber;
	}
	
	public void updateEmail(String newEmail){
		this.email = newEmail;
	}
	
	public void updatePassword(String newPassword){
		this.password = newPassword;
	}
	
	public void addFriend(PublicInfo friend){
		this.friends.add(friend);
	}
	
}
