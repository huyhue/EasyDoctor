package fpt.edu.vn.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {
	 
	@Column (name = "name")
	    private String name;
	
	@Column (name = "username")
    private String username;
	
	@Column (name = "password")
    private String password;
	
	@Column (name = "email")
    private String email;
	
	public Profile() {
		
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
}

