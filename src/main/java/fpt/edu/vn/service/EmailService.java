package fpt.edu.vn.service;

import org.thymeleaf.context.Context;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.User;

import java.io.File;

public interface EmailService {
    void sendEmail(String to, String subject, String templateName, Context templateContext, File attachment);

    void sendConfirmRegistration(User user);
    
    void sendAppointmentRejectionRequestedNotification(Appointment appointment);
    void sendAppointmentRejectionAcceptedNotification(Appointment appointment);
}
