package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.edu.vn.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
	Clinic findByName(String name);
	
}
