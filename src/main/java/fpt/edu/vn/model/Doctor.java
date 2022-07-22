package fpt.edu.vn.model;

import java.util.List;
import javax.persistence.*;

import fpt.edu.vn.component.UserForm;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "id_doctor")
public class Doctor extends User {
	private String description;
	
<<<<<<< HEAD
	private Integer startPracticeDate;
=======
	@Column(name = "name")
	    private String name;
	
	@Column(name = "phone")
	    private String phone;
	
	@Column(name = "editable", columnDefinition = "boolean default false")
    private boolean editable;
	
	private Date startPracticeDate;
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
	
	@OneToOne
    @JoinColumn(name = "id_specialty")
    private Specialty specialty;
	
	@ManyToOne
    @JoinColumn(name = "id_clinic")
    private Clinic clinic;
	
//	Booking doctor
	@OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "doctor")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(name = "packages_doctors", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_packages"))
    private List<Packages> packages;

    @OneToOne(mappedBy = "doctor", cascade = {CascadeType.ALL})
    private WorkingPlan workingPlan;
	
    public Doctor() {
    }
    
	@Override
	public void update(UserForm updateData) {
		super.update(updateData);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStartPracticeDate() {
		return startPracticeDate;
	}

	public void setStartPracticeDate(Integer startPracticeDate) {
		this.startPracticeDate = startPracticeDate;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Packages> getPackages() {
		return packages;
	}

	public void setPackages(List<Packages> packages) {
		this.packages = packages;
	}

	public WorkingPlan getWorkingPlan() {
		return workingPlan;
	}

	public void setWorkingPlan(WorkingPlan workingPlan) {
		this.workingPlan = workingPlan;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
}
