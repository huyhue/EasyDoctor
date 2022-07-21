package fpt.edu.vn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
	
	@Column(name = "feedback",length=100)
    private String feedback;
	
	@Column(name = "rating")
	private int rating;
	
	@ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
	
	@ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;
	
	 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	 @Column(name = "date")
	 private Date date;

    public Review() {
	}

	public Review(String feedback, int rating, Doctor doctor, Patient patient, Date date) {
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
}
