package fpt.edu.vn.model;

import javax.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;
    
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "address")
    private String address;
    
    @Column(name = "enabled", columnDefinition = "boolean default false")
	private boolean enabled;
	
	@Column(name = "confirmation_token")
	private String confirmationToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
				            name = "role_id", referencedColumnName = "id"))

	private Collection<Role> roles;

    public User() {
    }
    
    public User(String userName, String password, String email, String mobile,
			String address, Collection<Role> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.roles = roles;
	}
    

	public User(String userName, String email, String confirmationToken, Collection<Role> roles) {
		super();
		this.userName = userName;
		this.email = email;
		this.confirmationToken = confirmationToken;
		this.roles = roles;
	}

	public User(String password, boolean enabled, String confirmationToken) {
		super();
		this.password = password;
		this.enabled = enabled;
		this.confirmationToken = confirmationToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + ", mobile=" + mobile
				+ ", address=" + address + ", enabled=" + enabled + ", confirmationToken=" + confirmationToken
				+ ", roles=" + roles + "]";
	}
   
}
