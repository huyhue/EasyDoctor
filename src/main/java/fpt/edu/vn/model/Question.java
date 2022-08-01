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
	
	@Column(name = "problem")
	private String problem;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "responses")
	private String responses;
	
	public Question() {
	}

	public Question(String fullname, String mobile, String problem, String email, String description,
			String responses) {
		super();
		this.fullname = fullname;
		this.mobile = mobile;
		this.problem = problem;
		this.email = email;
		this.description = description;
		this.responses = responses;
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

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getResponses() {
		return responses;
	}

	public void setResponses(String responses) {
		this.responses = responses;
	}
	
}