package fpt.edu.vn.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
    @JoinColumn(name = "id_pathological")
    private Pathological pathological;
	
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
}
