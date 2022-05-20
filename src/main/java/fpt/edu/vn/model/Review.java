package fpt.edu.vn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
	
	@Column(name = "review")
    private String review;
	
	@Column(name = "rating")
	private int rating;
	
	@ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
	
	@ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    public Review() {
	}

	public Review(String review, int rating, Patient patient, Doctor doctor) {
		super();
		this.review = review;
		this.rating = rating;
		this.patient = patient;
		this.doctor = doctor;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
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
