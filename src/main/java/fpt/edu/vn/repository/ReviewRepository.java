package fpt.edu.vn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.Review;
import fpt.edu.vn.model.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	//SELECT AVG(rating) rating FROM review WHERE bookId = ?
	//return (rating == null) ? 0.0 : rating;
	@Query("select AVG(r.rating) from Review r inner join r.doctor d where d.id = :doctorId")
	double getRatingByDoctorId(@Param("doctorId") int doctorId);
}
