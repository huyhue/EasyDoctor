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

    public NotificationServiceImpl(@Value("${mailing.enabled}") boolean mailingEnabled, NotificationRepository notificationRepository, UserService userService, EmailService emailService) {
        this.mailingEnabled = mailingEnabled;
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.emailService = emailService;
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
//        newNotification(title, message, url, appointment.getPatient());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentRejectionAcceptedNotification(appointment);
        }
    }

//    @Override
//    public void newNewAppointmentScheduledNotification(Appointment appointment, boolean sendEmail) {
//        String title = "New appointment scheduled";
//        String message = "New appointment scheduled with" + appointment.getCustomer().getFirstName() + " " + appointment.getProvider().getLastName() + " on " + appointment.getStart().toString();
//        String url = "/appointments/" + appointment.getId();
//        newNotification(title, message, url, appointment.getProvider());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendNewAppointmentScheduledNotification(appointment);
//        }
//    }
//
//    @Override
//    public void newAppointmentCanceledByCustomerNotification(Appointment appointment, boolean sendEmail) {
//        String title = "Appointment Canceled";
//        String message = appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName() + " cancelled appointment scheduled at " + appointment.getStart().toString();
//        String url = "/appointments/" + appointment.getId();
//        newNotification(title, message, url, appointment.getProvider());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendAppointmentCanceledByCustomerNotification(appointment);
//        }
//    }
//
//    @Override
//    public void newAppointmentCanceledByProviderNotification(Appointment appointment, boolean sendEmail) {
//        String title = "Appointment Canceled";
//        String message = appointment.getProvider().getFirstName() + " " + appointment.getProvider().getLastName() + " cancelled appointment scheduled at " + appointment.getStart().toString();
//        String url = "/appointments/" + appointment.getId();
//        newNotification(title, message, url, appointment.getCustomer());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendAppointmentCanceledByProviderNotification(appointment);
//        }
//    }
//
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
