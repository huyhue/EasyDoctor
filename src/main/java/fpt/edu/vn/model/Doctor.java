package fpt.edu.vn.model;

import java.util.List;
import javax.persistence.*;

import fpt.edu.vn.component.UserForm;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "id_doctor")
public class Doctor extends User {
	private String description;

	private Integer startPracticeDate;

	@OneToOne
	@JoinColumn(name = "id_specialty")
	private Specialty specialty;

	@ManyToOne
	@JoinColumn(name = "id_clinic")
	private Clinic clinic;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Review> reviews;

	@ManyToMany
	@JoinTable(name = "patients_doctors", joinColumns = @JoinColumn(name = "id_doctor"), inverseJoinColumns = @JoinColumn(name = "id_patient"))
	private List<Patient> follower;

	@ManyToMany
	@JoinTable(name = "packages_doctors", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_packages"))
	private List<Packages> packages;

	@OneToOne(mappedBy = "doctor", cascade = { CascadeType.ALL })
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

	public List<Patient> getFollower() {
		return follower;
	}

	public void setFollower(List<Patient> follower) {
		this.follower = follower;
	}
}
