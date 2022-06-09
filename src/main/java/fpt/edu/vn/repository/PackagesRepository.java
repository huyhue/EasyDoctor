package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Packages;

import java.util.List;

public interface PackagesRepository extends JpaRepository<Packages, Integer> {
	
    @Query("select p from Packages p inner join p.doctors d where d.id in :doctorId")
    List<Packages> findPackagesByDoctorId(@Param("doctorId") int doctorId);
}
