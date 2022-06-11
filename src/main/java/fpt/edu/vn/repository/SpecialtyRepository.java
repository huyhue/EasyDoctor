package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fpt.edu.vn.model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
	
}
