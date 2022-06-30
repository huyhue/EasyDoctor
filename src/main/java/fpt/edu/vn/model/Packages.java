package fpt.edu.vn.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "packages")
public class Packages extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "editable", columnDefinition = "boolean default false")
    private boolean editable;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "packages_doctors", joinColumns = @JoinColumn(name = "id_packages"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> doctors;

    public Packages() {
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

	public List<User> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<User> doctors) {
		this.doctors = doctors;
	}

	public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Packages)) return false;
        Packages work = (Packages) o;
        return super.getId().equals(work.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
