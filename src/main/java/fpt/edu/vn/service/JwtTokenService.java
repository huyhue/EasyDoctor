package fpt.edu.vn.service;

import java.time.LocalDateTime;
import java.util.Date;

import fpt.edu.vn.model.Appointment;

public interface JwtTokenService {
    String generateAppointmentRejectionToken(Appointment appointment);

    String generateAcceptRejectionToken(Appointment appointment);

    boolean validateToken(String token);

    int getAppointmentIdFromToken(String token);

    int getPatientIdFromToken(String token);

    int getDoctorIdFromToken(String token);

    ////
    Date convertLocalDateTimeToDate(LocalDateTime localDateTime);
}
