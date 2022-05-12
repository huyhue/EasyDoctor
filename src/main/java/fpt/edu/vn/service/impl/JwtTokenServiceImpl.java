package fpt.edu.vn.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.service.JwtTokenService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

//@Slf4j
@Component
public class JwtTokenServiceImpl implements JwtTokenService {

    private String jwtSecret;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

    public JwtTokenServiceImpl(@Value(value = "${app.jwtSecret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public String generateAppointmentRejectionToken(Appointment appointment) {
        Date expiryDate = convertLocalDateTimeToDate(appointment.getEnd().plusDays(1));
        return Jwts.builder()
                .claim("appointmentId", appointment.getId())
                .claim("patientId", appointment.getPatient().getId())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public String generateAcceptRejectionToken(Appointment appointment) {
        return Jwts.builder()
                .claim("appointmentId", appointment.getId())
                .claim("doctorId", appointment.getDoctor().getId())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("Error while token {} validation, error is {}", token, e.getMessage());
        }
        return false;

    }

    @Override
    public int getAppointmentIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (int) claims.get("appointmentId");
    }

    @Override
    public int getPatientIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (int) claims.get("patientId");
    }

    @Override
    public int getDoctorIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (int) claims.get("doctorId");
    }

    @Override
    public Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);
        Instant instant = localDateTime.toInstant(zoneOffSet);
        return Date.from(instant);
    }
}
