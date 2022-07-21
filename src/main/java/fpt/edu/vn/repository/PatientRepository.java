package fpt.edu.vn.repository;

import java.util.Optional;

import fpt.edu.vn.model.Patient;

public interface PatientRepository extends CommonUserRepository<Patient> {
	Optional<Patient> findByUserName(String userName);
	Patient findByEmail(String email);
}
