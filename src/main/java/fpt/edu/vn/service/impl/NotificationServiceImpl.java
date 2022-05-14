package fpt.edu.vn.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Notification;
import fpt.edu.vn.model.User;
import fpt.edu.vn.repository.NotificationRepository;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.NotificationService;
import fpt.edu.vn.service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserService userService;
	private final EmailService emailService;
	private final boolean mailingEnabled;

	public NotificationServiceImpl(@Value("${mailing.enabled}") boolean mailingEnabled,
			NotificationRepository notificationRepository, UserService userService, EmailService emailService) {
		this.mailingEnabled = mailingEnabled;
		this.notificationRepository = notificationRepository;
		this.userService = userService;
		this.emailService = emailService;
	}

	@Override
	public void newNotification(String title, String message, String url, User user) {
		Notification notification = new Notification();
		notification.setTitle(title);
		notification.setUrl(url);
		notification.setCreatedAt(new Date());
		notification.setMessage(message);
		notification.setUser(user);
		notificationRepository.save(notification);
	}

	@Override
	public void markAsRead(int notificationId, int userId) {
		Notification notification = notificationRepository.findById(notificationId).get();
		if (notification.getUser().getId() == userId) {
			notification.setRead(true);
			notificationRepository.save(notification);
		} else {
			throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
		}
	}

	@Override
	public void markAllAsRead(int userId) {
		List<Notification> notifications = notificationRepository.getAllUnreadNotifications(userId);
		for (Notification notification : notifications) {
			notification.setRead(true);
			notificationRepository.save(notification);
		}
	}

	@Override
	public Notification getNotificationById(int notificationId) {
		return notificationRepository.findById(notificationId).get();
	}

	@Override
	public List<Notification> getAll(int userId) {
		return userService.getUserById(userId).getNotifications();
	}

	@Override
	public List<Notification> getUnreadNotifications(int userId) {
		return notificationRepository.getAllUnreadNotifications(userId);
	}

	@Override
	public void newAppointmentNotification(Appointment appointment, boolean sendEmail) {
		String title = "Cuộc hẹn mới đã lên lịch";
		String message = "Bác sĩ: " + appointment.getDoctor().getFullname() + " có cuộc hẹn khám bệnh với bệnh nhân: "
				+ appointment.getPatient().getFullname() + " vào lúc " + appointment.getStart().toString();
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getDoctor());
		if (sendEmail && mailingEnabled) {
			emailService.sendNewAppointment(appointment);
		}
	}

	@Override
	public void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail) {
		String title = "Appointment Rejected";
		String message = appointment.getPatient().getFullname() + "rejected an appointment. Your approval is required";
		String url = "/appointments/" + appointment.getId();
//        newNotification(title, message, url, appointment.getDoctor());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentRejectionRequestedNotification(appointment);
		}
	}

	@Override
	public void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail) {
		String title = "Rejection accepted";
		String message = "You doctor accepted your rejection request";
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getPatient());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentRejectionAcceptedNotification(appointment);
		}
	}

	@Override
	public void appointmentCanceledByPatient(Appointment appointment, boolean sendEmail) {
		String title = "Lịch khám đã bị hủy";
		String message = "Bệnh nhân " + appointment.getPatient().getFullname() + " đã hủy lịch khám vào lúc "
				+ appointment.getStart().toString();
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getDoctor());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentCanceledByPatient(appointment);
		}
	}

	@Override
	public void appointmentCanceledByDoctor(Appointment appointment, boolean sendEmail) {
		String title = "Lịch khám đã bị hủy";
		String message = "Bác sĩ " + appointment.getDoctor().getFullname() + " đã hủy lịch khám vào lúc "
				+ appointment.getStart().toString();
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getPatient());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentCanceledByDoctor(appointment);
		}
	}

//    public void newInvoice(Invoice invoice, boolean sendEmail) {
//        String title = "New invoice";
//        String message = "New invoice has been issued for you";
//        String url = "/invoices/" + invoice.getId();
//        newNotification(title, message, url, invoice.getAppointments().get(0).getCustomer());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendInvoice(invoice);
//        }
//    }
//
//
//    @Override
//    public void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail) {
//        String title = "Rejection accepted";
//        String message = "You provider accepted your rejection request";
//        String url = "/appointments/" + appointment.getId();
//        newNotification(title, message, url, appointment.getCustomer());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendAppointmentRejectionAcceptedNotification(appointment);
//        }
//    }

}
