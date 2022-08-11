package fpt.edu.vn.component;

import fpt.edu.vn.model.Packages;

public class PatientDto {
	private String id;
	private String userName;
	private String email;
	private String fullname;
	private String mobile;
	private String address;
	private boolean editable;

	public PatientDto(String id, String userName, String email, String fullname, String mobile, String address, boolean editable) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.fullname = fullname;
		this.mobile = mobile;
		this.address = address;
		this.editable = editable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

   
}
