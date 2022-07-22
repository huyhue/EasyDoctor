package fpt.edu.vn.repository;

<<<<<<< HEAD
import java.util.Optional;

import fpt.edu.vn.model.Patient;

public interface PatientRepository extends CommonUserRepository<Patient> {
	Optional<Patient> findByUserName(String userName);
	Patient findByEmail(String email);
=======
import java.util.List;

import fpt.edu.vn.model.Patient;

public interface PatientRepository extends CommonUserRepository <Patient> {
	
	
	List<Patient> findByName(String name);
	
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
}
