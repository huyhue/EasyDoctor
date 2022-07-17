package fpt.edu.vn.repository;

import java.util.List;

import fpt.edu.vn.model.Patient;

public interface PatientRepository extends CommonUserRepository <Patient> {
	
	
	List<Patient> findByName(String name);
	
}
