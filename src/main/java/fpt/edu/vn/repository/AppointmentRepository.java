package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.edu.vn.model.Appointment;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    
    @Query("select a from Appointment a where a.canceler.id = :userId")
    List<Appointment> findCanceledByUserId(@Param("userId") int userId);

    @Query("select a from Appointment a where  a.status='SCHEDULED' and (a.patient.id = :userId or a.doctor.id = :userId)")
    List<Appointment> findScheduledByUserId(@Param("userId") int userId);
    
    @Query("select COUNT(a) from Appointment a where a.doctor.id = :doctorId and DATE(a.start) =:date and a.status = 'SCHEDULED'")
    int countAppointmentByStatus(@Param("doctorId") int doctorId, @Param("date") Date date);
    
    @Query("select COUNT(a) from Appointment a where a.doctor.id = :doctorId and DATE(a.start) =:date and a.status = 'FINISHED'")
    int countAppointmentByStatus1(@Param("doctorId") int doctorId, @Param("date") Date date);
    
    @Query("select COUNT(a) from Appointment a where a.doctor.id = :doctorId and DATE(a.start) =:date and a.status = 'CONFIRMED'")
    int countAppointmentByStatus2(@Param("doctorId") int doctorId, @Param("date") Date date);
    
    @Query("select COUNT(a) from Appointment a where a.doctor.id = :doctorId and DATE(a.start) =:date and a.status = 'CANCELED'")
    int countAppointmentByStatus3(@Param("doctorId") int doctorId, @Param("date") Date date);
    
    @Query("select COUNT(a) from Appointment a where a.doctor.id = :doctorId and DATE(a.start) =:date and a.status = 'INVOICED'")
    int countAppointmentByStatus4(@Param("doctorId") int doctorId, @Param("date") Date date);
    
    @Query("select COUNT(a) from Appointment a where a.doctor.id = :doctorId and DATE(a.start) =:date and a.status = 'REJECTION_REQUESTED'")
    int countAppointmentByStatus5(@Param("doctorId") int doctorId, @Param("date") Date date);
    
    @Query("select a from Appointment a where a.id = :id and TIME(a.start) >=:timeStart and TIME(a.end) <=:timeEnd")
    List<Appointment> findAppointmentToCancel(@Param("id") int id, @Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd);
}
