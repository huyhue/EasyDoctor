package fpt.edu.vn.model;

import java.util.List;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clinics")
<<<<<<< HEAD
public class Clinic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
=======
public class Clinic extends BaseEntity {
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc

    
    @Column (name = "name")
    private String name;
    
    @Column (name = "address")
    private String address;
    
    @Column (name = "phone")
    private Integer phone;
    
    @Column (name = "website")
    private String website;
    
    @Column (name = "description")
    private String description;
    
    @JsonIgnore
<<<<<<< HEAD
	@OneToMany(mappedBy = "clinic")
	private List<Doctor> doctors;
=======
    @ManyToMany
    @JoinTable(name = "clinic_doctors",joinColumns = @JoinColumn(name = "id_clinic"),inverseJoinColumns = @JoinColumn(name = "id_user"))
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc

	private List<User> doctors;

<<<<<<< HEAD
    public Clinic(Integer id, String name, String address, String telephone, String website, String description,
			List<Doctor> doctors) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.website = website;
		this.description = description;
		this.doctors = doctors;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
=======
    public Clinic() {
    }

>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public List<User> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<User> doctors) {
		this.doctors = doctors;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clinic)) return false;
        Clinic work = (Clinic) o;
        return super.getId().equals(work.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}