package fpt.edu.vn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "declarations")
public class Declaration {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "blood")
    private String blood;
	
	@Column(name = "background")
	private String background;
	
	@Column(name = "medicine")
	private String medicine;
	
	@Column(name = "symptom")
	private String symptom;
	
	@Column(name = "notes")
	private String notes;
    
    @OneToOne(mappedBy = "declaration", cascade = {CascadeType.ALL})
    private Patient patient;
    
	public Declaration() {
	}
}
