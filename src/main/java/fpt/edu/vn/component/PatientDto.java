package fpt.edu.vn.component;

public class PatientDto {
<<<<<<< HEAD
	private String id;
	private String userName;
	private String email;
	private String fullname;
	private String mobile;
	private String address;

	public PatientDto(String id, String userName, String email, String fullname, String mobile, String address) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.fullname = fullname;
		this.mobile = mobile;
		this.address = address;
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
}
=======
	
		private Integer id;
		
		private String userName;
		
		private String email;
		
		private String fullname;
		
		private String mobile;
	
		public PatientDto(Integer id, String userName, String email, String fullname, String mobile) {
			super();
			this.id = id;
			this.userName = userName;
			this.email = email;
			this.fullname = fullname;
			this.mobile = mobile;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
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
}

>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
