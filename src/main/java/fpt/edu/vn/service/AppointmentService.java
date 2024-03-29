package fpt.edu.vn.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import fpt.edu.vn.component.AppoinmentDto;
import fpt.edu.vn.component.ChatMessage;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.Review;

public interface AppointmentService {
	
	//Get Available Hour
	List<TimePeroid> getAvailableHours(int doctorId, int patientId, int packagesId, LocalDate date);
	List<AppoinmentDto> getAppointmentByDate(String start, String end);
	List<AppoinmentDto> getAppointmentByMonth(String month);
	List<Appointment> getAppointmentsByDoctorAtDay(int doctorId, LocalDate day);
	List<Appointment> getAppointmentsByPatientAtDay(int patientId, LocalDate day);
	List<TimePeroid> excludeAppointmentsFromTimePeroids(List<TimePeroid> peroids, List<Appointment> appointments);
	List<TimePeroid> calculateAvailableHours(List<TimePeroid> availableTimePeroids, Packages packages);
	
	void createNewAppointment(int packagesId, int doctorId, int patientId, LocalDateTime start);
	void cancelUserAppointmentById(int appointmentId, int userId);
	void updateAppointment(Appointment appointment);
	
	//Number
	int getNumberScheduledAppointmentByUserId(int userId);
	int getNumberCanceledAppointmentByUserId(int userId);
	
	boolean isAvailable(int packagesId, int doctorId, int patientId, LocalDateTime start);
	
	List<Appointment> getAllAppointments();
	List<AppoinmentDto> getAllAppointment();
	List<Appointment> getAppointmentByPatientId(int patientId);
	List<Appointment> getAppointmentByDoctorId(int doctorId);
	
	Appointment getAppointmentByIdWithAuthorization(int id);
	Appointment getAppointmentById(int id);
	
	void cancelAppointmentByChangeWorkingPlan(int doctorId);
	
	//Cancel
	String getCancelNotAllowedReason(int userId, int appointmentId);
	CommonMsg cancelAppointmentByAdmin(int appointmentId);
	List<Appointment> getCanceledAppointmentsByPatientIdForCurrentMonth(int patientId);
	
	//Reject
	boolean isPatientAllowedToRejectAppointment(int userId, int appointmentId);
	boolean isDoctorAllowedToAcceptRejection(int doctorId, int appointmentId);
	boolean requestAppointmentRejection(int appointmentId, int patientId);
	boolean requestAppointmentRejection(String token);
	boolean acceptRejection(int appointmentId, int patientId);
	boolean acceptRejection(String token);
	
	//Chat
	ChatMessage addMessageToAppointmentChat(ChatMessage chatMessage);
	List<ChatMessage> getMessagesByAppointmentId(int appointmentId);
	Boolean getActiveUserByAppointment(int appointmentId, int userId);
	List<ChatMessage> searchContentInMessages(String content);
	
	//Auto
	void updateUserAppointmentsStatuses(int userId);
	void updateAllAppointmentsStatuses();
	
	List<Appointment> getConfirmedAppointmentsByPatientId(int patientId);
	
	//Review
	boolean isPatientAllowedToReview(int userId, int appointmentId);
	void saveReviewByAppointment(Review revieww, int appointmentId);
	
	//Statitics
	long[] getCountAppointmentByStatus(int doctorId, String dateTime);
	int countAllAppointmentByMonth(int month);
}
