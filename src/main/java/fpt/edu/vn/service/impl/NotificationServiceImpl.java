package fpt.edu.vn.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Invoice;
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
		String title = "Cu???c h???n m???i ???? l??n l???ch";
		String message = "B??c s??: " + appointment.getDoctor().getFullname() + " c?? cu???c h???n kh??m b???nh v???i b???nh nh??n: "
				+ appointment.getPatient().getFullname() + " v??o l??c " + appointment.getStart().toString();
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getDoctor());
		if (sendEmail && mailingEnabled) {
			emailService.sendNewAppointment(appointment);
		}
	}

	@Override
	public void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail) {
		String title = "L???ch kh??m ???? b??? t??? ch???i";
		String message = "B???nh nh??n " + appointment.getPatient().getFullname() + " ???? t??? ch???i l???ch kh??m. S??? ch???p thu???n c???a b???n l?? c???n thi???t";
		String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getDoctor());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentRejectionRequested(appointment);
		}
	}

	@Override
	public void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail) {
		String title = "L???i t??? ch???i ???????c ch???p nh???n";
		String message = "B??c s?? c???a b???n ???? ch???p nh???n y??u c???u t??? ch???i c???a b???n";
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getPatient());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentRejectionAccepted(appointment);
		}
	}

	@Override
	public void appointmentCanceledByPatient(Appointment appointment, boolean sendEmail) {
		String title = "L???ch kh??m ???? b??? h???y";
		String message = "B???nh nh??n " + appointment.getPatient().getFullname() + " ???? h???y l???ch kh??m v??o l??c "
				+ appointment.getStart().toString();
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getDoctor());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentCanceledByPatient(appointment);
		}
	}

	@Override
	public void appointmentCanceledByDoctor(Appointment appointment, boolean sendEmail) {
		String title = "L???ch kh??m ???? b??? h???y";
		String message = "B??c s?? " + appointment.getDoctor().getFullname() + " ???? h???y l???ch kh??m v??o l??c "
				+ appointment.getStart().toString();
		String url = "/appointments/" + appointment.getId();
		newNotification(title, message, url, appointment.getPatient());
		if (sendEmail && mailingEnabled) {
			emailService.sendAppointmentCanceledByDoctor(appointment);
		}
	}
	
	@Override
    public void newAppointmentFinishedNotification(Appointment appointment, boolean sendEmail) {
        String title = "L???ch kh??m ???? k???t th??c";
        String message = "L???ch kh??m ???? k???t th??c, b???n c?? th??? t??? ch???i cu???c h???n n???u n?? kh??ng di???n ra cho ?????n khi " + appointment.getEnd().plusHours(24).toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getPatient());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentFinished(appointment);
        }
    }
	
	public void newInvoice(Invoice invoice, boolean sendEmail) {
        String title = "Xu???t h??a ????n";
        String message = "Xu???t h??a ????n ???? ???????c g???i t???i b???n";
        String url = "/invoices/" + invoice.getId();
        newNotification(title, message, url, invoice.getAppointments().get(0).getPatient());
        if (sendEmail && mailingEnabled) {
            emailService.sendInvoice(invoice);
        }
    }

}
