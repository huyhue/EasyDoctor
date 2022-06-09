package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Declaration;

public interface DeclarationRepository extends JpaRepository<Declaration, Integer> {
	
	@Query("select d from Declaration d where d.patient.id = :patientId")
	Declaration getDeclarationByPatientId(@Param("patientId") int patientId);
}
