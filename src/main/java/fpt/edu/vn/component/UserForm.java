package fpt.edu.vn.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Gender;
import fpt.edu.vn.model.User;

public class UserForm {
    private int id;
    
    @NotBlank(message = "Enter username")
    @Size(min = 5, max = 15, message = "Username should have 5-15 letters")
    private String userName;

//    @NotBlank(message = "Enter password")
//    @Length(min = 6, message = "Password must be at least 6 characters")
//    private String password;
//
//    @NotBlank(message = "Re Enter password")
//    private String matchingPassword;

    @NotBlank(message = "Enter email address")
    @Email(message = "Enter a valid Email address")
    private String email;

    @NotBlank(message = "Enter mobile")
    private String mobile;
    
    @NotBlank(message = "Enter fullname")
    private String fullname;
    
    @Nullable
	private Integer age;
    
    @Nullable
	private Gender gender;
    
    @Nullable
    private String address;
    
    @Nullable
    private String profileImage;

    @Nullable
    private String description;
	
    
	private Date startPracticeDate;
	
    private String certification;
    
    public UserForm() {
	}

    public UserForm(User user) {
        this.setId(user.getId());
        this.setUserName(user.getUserName());
        this.setEmail(user.getEmail());
        this.setMobile(user.getMobile());
        this.setGender(user.getGender());
    }

    public UserForm(Doctor doctor) {
        this((User) doctor);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getMatchingPassword() {
//		return matchingPassword;
//	}
//
//	public void setMatchingPassword(String matchingPassword) {
//		this.matchingPassword = matchingPassword;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartPracticeDate() {
		return startPracticeDate;
	}

	public void setStartPracticeDate(Date startPracticeDate) {
		this.startPracticeDate = startPracticeDate;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}
    
    
}
