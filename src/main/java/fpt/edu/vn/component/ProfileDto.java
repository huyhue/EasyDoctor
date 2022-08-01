package fpt.edu.vn.component;

public class ProfileDto {
	private String fullname;
	private String email;
	private String newPassword;
	private String userName;
	
	public ProfileDto(String fullname, String email, String newPassword, String userName) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.newPassword = newPassword;
		this.userName = userName;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}