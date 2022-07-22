package fpt.edu.vn.component;

public class ReviewDto {
	 private String feedback;
	 
	 private int rating;
	 
	 private String patient;
	 
	 private String doctor;

	public ReviewDto(String feedback, int rating, String patient, String doctor) {
		super();
		this.feedback = feedback;
		this.rating = rating;
		this.patient = patient;
		this.doctor = doctor;
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

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	 
}