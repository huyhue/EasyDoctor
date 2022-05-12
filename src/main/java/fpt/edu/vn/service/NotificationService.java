package fpt.edu.vn.service;

import java.util.List;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.ChatMessage;
import fpt.edu.vn.model.ExchangeRequest;
import fpt.edu.vn.model.Invoice;
import fpt.edu.vn.model.Notification;
import fpt.edu.vn.model.User;

public interface NotificationService {
	void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail);
}
