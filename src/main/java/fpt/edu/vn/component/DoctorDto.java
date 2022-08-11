package fpt.edu.vn.component;

public class DoctorDto {
	private String id;
	private String userName;
	private String email;
	private String fullname;
	private String nameSpecialty;
	private String nameClinic;
	private boolean editable;

	public DoctorDto(String id, String userName, String email, String fullname, String nameSpecialty,
			String nameClinic, boolean editable ) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.fullname = fullname;
		this.nameSpecialty = nameSpecialty;
		this.nameClinic = nameClinic;
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

	public String getNameSpecialty() {
		return nameSpecialty;
	}

	public void setNameSpecialty(String nameSpecialty) {
		this.nameSpecialty = nameSpecialty;
	}

	public String getNameClinic() {
		return nameClinic;
	}

	public void setNameClinic(String nameClinic) {
		this.nameClinic = nameClinic;
	}
	
	public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
