package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.History;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
	
	@Query("select h from History h where h.patient.id = :patientId and h.pulished = True")
	List<History> findByPatientId(@Param("patientId") int patientId);
	
	@Query("select h from History h where h.appointment.id = :appointmentId")
	History getHistoryByAppointmentId(@Param("appointmentId") int appointmentId);
}
