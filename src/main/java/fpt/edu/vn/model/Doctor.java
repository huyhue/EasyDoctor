package fpt.edu.vn.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "id_doctor")
public class Doctor extends User {
	private String description;
	private Date startPracticeDate;
	
	@ManyToOne
    @JoinColumn(name = "clinics_id")
    private Clinic clinic;
	
    public Doctor() {
    }

}
