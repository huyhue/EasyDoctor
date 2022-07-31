package fpt.edu.vn.service;

import java.util.List;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Invoice;
import fpt.edu.vn.model.Notification;
import fpt.edu.vn.model.User;

public interface NotificationService {
	void newNotification(String title, String message, String url, User user);
	void markAsRead(int notificationId, int userId);
	void markAllAsRead(int userId);
	Notification getNotificationById(int notificationId);
	List<Notification> getAll(int userId);
	List<Notification> getUnreadNotifications(int userId);
	
	void newAppointmentNotification(Appointment appointment, boolean sendEmail);
	void appointmentCanceledByPatient(Appointment appointment, boolean sendEmail);
	void appointmentCanceledByDoctor(Appointment appointment, boolean sendEmail);
	void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail);
	void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail);
	void newAppointmentFinishedNotification(Appointment appointment, boolean sendEmail);
	void newPostNotificationByDoctor(int doctorId, String content);
	
	void newInvoice(Invoice invoice, boolean sendEmail);
}
