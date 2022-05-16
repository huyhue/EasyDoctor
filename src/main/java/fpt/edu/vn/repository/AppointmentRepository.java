package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query("select a from Appointment a where a.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") int doctorId);
	
	@Query("select a from Appointment a where a.patient.id = :patientId")
	List<Appointment> findByPatientId(@Param("patientId") int patientId);
	
	@Query("select a from Appointment a where a.doctor.id = :doctorId and  a.start >=:dayStart and  a.start <=:dayEnd")
    List<Appointment> findByDoctorIdWithStartInPeroid(@Param("doctorId") int doctorId, @Param("dayStart") LocalDateTime startPeroid, @Param("dayEnd") LocalDateTime endPeroid);

    @Query("select a from Appointment a where a.patient.id = :patientId and  a.start >=:dayStart and  a.start <=:dayEnd")
    List<Appointment> findByPatientIdWithStartInPeroid(@Param("patientId") int patientId, @Param("dayStart") LocalDateTime startPeroid, @Param("dayEnd") LocalDateTime endPeroid);

    @Query("select a from Appointment a where a.patient.id = :patientId and a.canceler.id =:patientId and a.canceledAt >=:date")
    List<Appointment> findByPatientIdCanceledAfterDate(@Param("patientId") int patientId, @Param("date") LocalDateTime date);

    @Query("select a from Appointment a where a.status = 'SCHEDULED' and :date >= a.end and (a.patient.id = :userId or a.doctor.id = :userId)")
    List<Appointment> findScheduledByUserIdWithEndBeforeDate(@Param("date") LocalDateTime date, @Param("userId") int userId);

    @Query("select a from Appointment a where a.status = 'FINISHED' and :date >= a.end and (a.patient.id = :userId or a.doctor.id = :userId)")
    List<Appointment> findFinishedByUserIdWithEndBeforeDate(@Param("date") LocalDateTime date, @Param("userId") int userId);

    @Query("select a from Appointment a where a.status = 'SCHEDULED' and :now >= a.end")
    List<Appointment> findScheduledWithEndBeforeDate(@Param("now") LocalDateTime now);
    
    @Query("select a from Appointment a where a.status = 'FINISHED' and :date >= a.end")
    List<Appointment> findFinishedWithEndBeforeDate(@Param("date") LocalDateTime date);
    
    @Query("select a from Appointment a where a.status = 'CONFIRMED' and a.patient.id = :patientId")
    List<Appointment> findConfirmedByPatientId(@Param("patientId") int patientId);
}
