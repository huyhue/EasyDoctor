package fpt.edu.vn.component;

import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Patient;

public class ReviewForm {
    private String feedback;
	private int rating;
	private int appointmentId;	
    private Patient patient;
    private Doctor doctor;

    public ReviewForm() {
	}

	public ReviewForm(String feedback, int rating, Patient patient, Doctor doctor) {
		super();
		this.feedback = feedback;
		this.rating = rating;
		this.patient = patient;
		this.doctor = doctor;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
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

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
    
}
