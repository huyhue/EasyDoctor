package fpt.edu.vn.component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

import fpt.edu.vn.model.AppointmentStatus;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.Patient;

public class AppoinmentDto {
	private Integer id;
	
	private LocalDateTime start;
	
	private LocalDateTime end;
	
	private Date date;
	
	private String status;
	
	private String patient;
	
	private String doctor;
	
	private String packages;

	public AppoinmentDto(Integer id, LocalDateTime start, LocalDateTime end, Date date, String status,
			String patient, String doctor, String packages) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.date = date;
		this.status = status;
		this.patient = patient;
		this.doctor = doctor;
		this.packages = packages;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}
	

}
