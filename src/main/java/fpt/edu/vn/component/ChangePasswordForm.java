package fpt.edu.vn.component;

public class ChangePasswordForm {
    
	private int id;
    private String currentPassword;
    private String password;
 
    public ChangePasswordForm() {
	}
    
    public ChangePasswordForm(int id) {
        this.id = id;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
