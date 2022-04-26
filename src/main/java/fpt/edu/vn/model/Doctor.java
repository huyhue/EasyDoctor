package fpt.edu.vn.model;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "id_doctor")
public class Doctor extends User {
	private String description;
	
	private Date startPracticeDate;
	
	@OneToOne
    @JoinColumn(name = "id_specialty")
    private Specialty specialty;
	
	@ManyToOne
    @JoinColumn(name = "id_clinic")
    private Clinic clinic;
	
    public Doctor() {
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartPracticeDate() {
		return startPracticeDate;
	}

	public void setStartPracticeDate(Date startPracticeDate) {
		this.startPracticeDate = startPracticeDate;
	}


	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

}
