package fpt.edu.vn.repository;

import java.util.List;

import fpt.edu.vn.model.Doctor;

public interface DoctorRepository extends CommonUserRepository<Doctor> {
	  
	    Doctor findByName(String name);

		List<Doctor> findBySpecialtyId(int specialtyId);

	}

