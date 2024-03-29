package fpt.edu.vn.component;

public class ReviewDto {
	private String id;
	private String feedback;
	private int rating;
	private String patient;
	private int doctorId;
	private String doctor;

	public ReviewDto(String id, String feedback, int rating, String patient, int doctorId, String doctor) {
		super();
		this.id = id;
		this.feedback = feedback;
		this.rating = rating;
		this.patient = patient;
		this.doctorId = doctorId;
		this.doctor = doctor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

}