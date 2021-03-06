package fpt.edu.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	@Query("select m from Message m where m.appointment.id = :appointmentId")
    List<Message> findByAppointmentId(@Param("appointmentId") int appointmentId);
	
	//SELECT p FROM Message p WHERE p.content LIKE '" + text + "%'"
	@Query("select m from Message m where m.message like %:content%")
	List<Message> findMessagesByContaining(@Param("content") String content);
	
}
