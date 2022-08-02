package fpt.edu.vn.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "id_patient")
public class Patient extends User {
	
	@Column(name = "address")
    private String address;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_declaration", referencedColumnName = "id")
    private Declaration declaration;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	private List<History> histories;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Review> reviews;

	@ManyToMany
    @JoinTable(name = "patients_doctors", joinColumns = @JoinColumn(name = "id_patient"), inverseJoinColumns = @JoinColumn(name = "id_doctor"))
    private List<Doctor> doctors;
	
	public Patient() {
		super();
	}
	
	public Patient(String userName, String password, String confirmationToken, Collection<Role> roles) {
		super(userName, password, confirmationToken, roles);
	}
	
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(Declaration declaration) {
		this.declaration = declaration;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<History> getHistories() {
		return histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

}