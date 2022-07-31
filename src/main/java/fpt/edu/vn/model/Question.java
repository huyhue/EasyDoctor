package fpt.edu.vn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="questions")
public class Question extends BaseEntity {
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "description")
	private String description;

	public Question(String fullname, String mobile, String email, String description) {
		this.fullname = fullname;
		this.mobile = mobile;
		this.email = email;
		this.description = description;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}