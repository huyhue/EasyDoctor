package fpt.edu.vn.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "id_patient")
public class Patient extends User {

	public Patient() {
	}
	

}
