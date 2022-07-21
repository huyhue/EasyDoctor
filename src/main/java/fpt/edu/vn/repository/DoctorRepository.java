package fpt.edu.vn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Doctor;

public interface DoctorRepository extends CommonUserRepository<Doctor> {
	
	@Query("select d from Doctor d where d.specialty.id = :specialtyId")
    List<Doctor> findBySpecialtyId(@Param("specialtyId") int specialtyId);
	
	Optional<Doctor> findByUserName(String userName);
	Doctor findByEmail(String email);
}
