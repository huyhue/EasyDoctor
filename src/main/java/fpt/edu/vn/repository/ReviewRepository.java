package fpt.edu.vn.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	@Query("select AVG(r.rating) from Review r inner join r.doctor d where d.id = :doctorId")
	Double getRatingByDoctorId(@Param("doctorId") int doctorId);
	
	@Query("select r from Review r where r.doctor.id = :doctorId")
    List<Review> getAllReviewByDoctorId(@Param("doctorId") int doctorId);
}
