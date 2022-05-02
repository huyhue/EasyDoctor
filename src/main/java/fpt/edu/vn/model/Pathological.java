package fpt.edu.vn.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pathologicals")
public class Pathological {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne(mappedBy = "pathological", cascade = {CascadeType.ALL})
    private Patient patient;
    
	public Pathological() {
	}
}
