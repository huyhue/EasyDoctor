package fpt.edu.vn.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import fpt.edu.vn.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
	Clinic findByName(String name);
=======
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Clinic;



public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
	@Query("select c from Clinic c inner join c.doctors d where d.id in :doctorId")
	  List<Clinic> findClinicByDoctorId(@Param("doctorId") int doctorId);
	  
	  Clinic findByName(String name);
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
}
