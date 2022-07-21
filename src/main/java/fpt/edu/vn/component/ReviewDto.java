package fpt.edu.vn.component;

import java.util.Date;

import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Patient;

public class ReviewDto {
	 private String feedback;
	 
	 private int rating;
	 
	 private String patient;
	 
	 private String doctor;
	 
	 private Date date;

	public ReviewDto(String feedback, int rating, String patient, String doctor, Date date) {
		super();
		this.feedback = feedback;
		this.rating = rating;
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	 
}
