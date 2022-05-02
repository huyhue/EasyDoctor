package fpt.edu.vn.component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordForm {
	@NotNull
    private int id;

    @Size(min = 5, max = 10, message = "Password should have 5-15 letters")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Size(min = 5, max = 10, message = "Password should have 5-15 letters")
    @NotBlank(message = "Matching Password is mandatory")
    private String matchingPassword;

    private String currentPassword;
 
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

	public ChangePasswordForm(@NotNull int id,
			@Size(min = 5, max = 10, message = "Password should have 5-15 letters") @NotBlank String password,
			@Size(min = 5, max = 10, message = "Password should have 5-15 letters") @NotBlank String matchingPassword,
			String currentPassword) {
		super();
		this.id = id;
		this.password = password;
		this.matchingPassword = matchingPassword;
		this.currentPassword = currentPassword;
	}
    
    
}
