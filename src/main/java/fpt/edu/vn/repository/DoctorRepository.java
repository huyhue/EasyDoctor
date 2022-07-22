package fpt.edu.vn.repository;

import java.util.List;
import java.util.Optional;

import fpt.edu.vn.model.Doctor;

public interface DoctorRepository extends CommonUserRepository<Doctor> {
<<<<<<< HEAD
	
	@Query("select d from Doctor d where d.specialty.id = :specialtyId")
    List<Doctor> findBySpecialtyId(@Param("specialtyId") int specialtyId);
	
	Optional<Doctor> findByUserName(String userName);
	Doctor findByEmail(String email);
}
=======
	  
	    Doctor findByName(String name);

		List<Doctor> findBySpecialtyId(int specialtyId);

	}

>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
