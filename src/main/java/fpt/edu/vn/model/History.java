package fpt.edu.vn.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "histories")
public class History extends BaseEntity  {
	@Column(name = "doctor")
	private String doctor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "startedAt")
	private LocalDateTime startedAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "updatedAt")
	private LocalDateTime updatedAt;
	
	@Column(name = "medicine")
	private String medicine;

	@Column(name = "diagnose")
	private String diagnose;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "advice")
	private String advice;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "result", nullable = true)
	private String result;
	
	@Column(name = "pulished", columnDefinition = "boolean default false")
	private boolean pulished;
	
	@ManyToOne
	@JoinColumn(name = "id_patient")
	private Patient patient;

	public History() {
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public boolean isPulished() {
		return pulished;
	}

	public void setPulished(boolean pulished) {
		this.pulished = pulished;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}
	
}