package fpt.edu.vn.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import fpt.edu.vn.component.UserForm;

import java.util.Collection;
import java.util.List;
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

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "age")
	private Integer age;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Column(name = "profile_img", nullable = true)
	private String profileImage;

	@Column(name = "enabled", columnDefinition = "boolean default false")
	private boolean enabled;

	@Column(name = "confirmation_token")
	private String confirmationToken;
	
	@OneToMany(mappedBy = "user")
    private List<Notification> notifications;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

	private Collection<Role> roles;

	public User() {
	}

	public User(String userName, String password, String email, String mobile, String fullname, Integer age,
			Gender gender, String profileImage, boolean enabled, String confirmationToken, Collection<Role> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.fullname = fullname;
		this.age = age;
		this.gender = gender;
		this.profileImage = profileImage;
		this.enabled = enabled;
		this.confirmationToken = confirmationToken;
		this.roles = roles;
	}

	public User(String userName, String password, String confirmationToken, Collection<Role> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.confirmationToken = confirmationToken;
		this.roles = roles;
	}

	public User(String password, boolean enabled, String confirmationToken) {
		super();
		this.password = password;
		this.enabled = enabled;
		this.confirmationToken = confirmationToken;
	}
	
	public void update(UserForm updateData) {
        this.setEmail(updateData.getEmail());
        this.setMobile(updateData.getMobile());
        this.setFullname(updateData.getFullname());
        this.setAge(updateData.getAge());
        this.setGender(updateData.getGender());
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

	public boolean isEnabled() {
		return enabled;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Transient
	public String getProfileImagePath() {
		if (profileImage == null) {
			return "/uploads/default/default.jpg";
		}

		return "/uploads/" + getEmail() + "/" + getProfileImage();
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
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
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
				+ ", fullname=" + fullname + ", age=" + age + ", gender=" + gender + ", profileImage=" + profileImage
				+ ", enabled=" + enabled + ", confirmationToken=" + confirmationToken + ", roles=" + roles + "]";
	}

	

}
