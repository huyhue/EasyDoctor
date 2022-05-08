package fpt.edu.vn.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.ChatMessage;
import fpt.edu.vn.model.Packages;

public interface AppointmentService {
	
	//Get Available Hour
	List<TimePeroid> getAvailableHours(int doctorId, int patientId, int packagesId, LocalDate date);
	List<Appointment> getAppointmentsByDoctorAtDay(int doctorId, LocalDate day);
	List<Appointment> getAppointmentsByPatientAtDay(int patientId, LocalDate day);
	List<TimePeroid> excludeAppointmentsFromTimePeroids(List<TimePeroid> peroids, List<Appointment> appointments);
	List<TimePeroid> calculateAvailableHours(List<TimePeroid> availableTimePeroids, Packages packages);
	
	boolean isAvailable(int packagesId, int doctorId, int patientId, LocalDateTime start);
	
	List<Appointment> getAllAppointments();
	List<Appointment> getAppointmentByDoctorId(int doctorId);
}
