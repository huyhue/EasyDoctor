package fpt.edu.vn.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import fpt.edu.vn.component.ChatMessage;
import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Message;
import fpt.edu.vn.model.Packages;

public interface AppointmentService {
	
	//Get Available Hour
	List<TimePeroid> getAvailableHours(int doctorId, int patientId, int packagesId, LocalDate date);
	List<Appointment> getAppointmentsByDoctorAtDay(int doctorId, LocalDate day);
	List<Appointment> getAppointmentsByPatientAtDay(int patientId, LocalDate day);
	List<TimePeroid> excludeAppointmentsFromTimePeroids(List<TimePeroid> peroids, List<Appointment> appointments);
	List<TimePeroid> calculateAvailableHours(List<TimePeroid> availableTimePeroids, Packages packages);
	
	void createNewAppointment(int packagesId, int doctorId, int patientId, LocalDateTime start);
	void cancelUserAppointmentById(int appointmentId, int userId);
	void updateAppointment(Appointment appointment);
	
	boolean isAvailable(int packagesId, int doctorId, int patientId, LocalDateTime start);
	
	List<Appointment> getAllAppointments();
	List<Appointment> getAppointmentByPatientId(int patientId);
	List<Appointment> getAppointmentByDoctorId(int doctorId);
	
	Appointment getAppointmentByIdWithAuthorization(int id);
	Appointment getAppointmentById(int id);
	
	//Cancel
	String getCancelNotAllowedReason(int userId, int appointmentId);
	List<Appointment> getCanceledAppointmentsByPatientIdForCurrentMonth(int patientId);
	
	//Reject
	boolean isPatientAllowedToRejectAppointment(int userId, int appointmentId);
	boolean isDoctorAllowedToAcceptRejection(int doctorId, int appointmentId);
	boolean requestAppointmentRejection(int appointmentId, int patientId);
	boolean requestAppointmentRejection(String token);
	boolean acceptRejection(int appointmentId, int patientId);
	boolean acceptRejection(String token);
	
	//Chat
	void addMessageToAppointmentChat(ChatMessage chatMessage);
	List<ChatMessage> getMessagesByAppointmentId(int appointmentId);
	
	//Auto
	void updateUserAppointmentsStatuses(int userId);
	void updateAllAppointmentsStatuses();
	
	List<Appointment> getConfirmedAppointmentsByPatientId(int patientId);
	
	boolean isPatientAllowedToReview(int userId, int appointmentId);
}
