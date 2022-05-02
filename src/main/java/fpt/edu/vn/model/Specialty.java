package fpt.edu.vn.model;

import javax.persistence.*;

@Entity
@Table(name = "specialties")
public class Specialty{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;
    
    @OneToOne(mappedBy = "specialty", cascade = {CascadeType.ALL})
    private Doctor doctor;

    public Specialty() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
