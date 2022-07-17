package fpt.edu.vn.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
	
	@OneToOne
    @JoinColumn(name = "id_declaration")
    private Declaration declaration;
	
	@Column(name = "name")
    private String name;
	
	@OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "patient")
	private List<History> histories;
	
	@OneToMany(mappedBy = "patient")
    private List<Review> reviews;
	
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
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
