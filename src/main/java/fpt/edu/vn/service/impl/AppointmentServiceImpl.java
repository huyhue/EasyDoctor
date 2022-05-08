package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import fpt.edu.vn.component.DayPlan;
import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.WorkingPlan;
import fpt.edu.vn.repository.AppointmentRepository;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final int NUMBER_OF_ALLOWED_CANCELATIONS_PER_MONTH = 1;
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final PackagesService packagesService;
//    private final ChatMessageRepository chatMessageRepository;
//    private final NotificationService notificationService;
//    private final JwtTokenServiceImpl jwtTokenService;
//
//    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService, WorkService workService, ChatMessageRepository chatMessageRepository, NotificationService notificationService, JwtTokenServiceImpl jwtTokenService) {
//        this.appointmentRepository = appointmentRepository;
//        this.userService = userService;
//        this.workService = workService;
//        this.chatMessageRepository = chatMessageRepository;
//        this.notificationService = notificationService;
//        this.jwtTokenService = jwtTokenService;
//    }
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService,
			PackagesService packagesService) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.userService = userService;
		this.packagesService = packagesService;
	}
	
	@Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

	@Override
    @PreAuthorize("#doctorId == principal.id")
    public List<Appointment> getAppointmentByDoctorId(int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
	
	@Override
    public List<TimePeroid> getAvailableHours(int doctorId, int patientId, int packagesId, LocalDate date) {
        Doctor d = userService.getDoctorById(doctorId);
        WorkingPlan workingPlan = d.getWorkingPlan();
        DayPlan selectedDay = workingPlan.getDay(date.getDayOfWeek().toString().toLowerCase());

        List<Appointment> doctorAppointments = getAppointmentsByDoctorAtDay(doctorId, date);
        List<Appointment> patientAppointments = getAppointmentsByPatientAtDay(patientId, date);

        List<TimePeroid> availablePeroids = selectedDay.getTimePeroidsWithBreaksExcluded();
        availablePeroids = excludeAppointmentsFromTimePeroids(availablePeroids, doctorAppointments);
        availablePeroids = excludeAppointmentsFromTimePeroids(availablePeroids, patientAppointments);
        
        return calculateAvailableHours(availablePeroids, packagesService.getPackagesById(packagesId));
    }
	
	@Override
    public List<Appointment> getAppointmentsByDoctorAtDay(int doctorId, LocalDate day) {
        return appointmentRepository.findByDoctorIdWithStartInPeroid(doctorId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    @Override
    public List<Appointment> getAppointmentsByPatientAtDay(int patientId, LocalDate day) {
        return appointmentRepository.findByPatientIdWithStartInPeroid(patientId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }
    
    @Override
    public List<TimePeroid> excludeAppointmentsFromTimePeroids(List<TimePeroid> peroids, List<Appointment> appointments) {

        List<TimePeroid> toAdd = new ArrayList();
        Collections.sort(appointments);
        for (Appointment appointment : appointments) {
            for (TimePeroid peroid : peroids) {
                if ((appointment.getStart().toLocalTime().isBefore(peroid.getStart()) || appointment.getStart().toLocalTime().equals(peroid.getStart())) && appointment.getEnd().toLocalTime().isAfter(peroid.getStart()) && appointment.getEnd().toLocalTime().isBefore(peroid.getEnd())) {
                    peroid.setStart(appointment.getEnd().toLocalTime());
                }
                if (appointment.getStart().toLocalTime().isAfter(peroid.getStart()) && appointment.getStart().toLocalTime().isBefore(peroid.getEnd()) && appointment.getEnd().toLocalTime().isAfter(peroid.getEnd()) || appointment.getEnd().toLocalTime().equals(peroid.getEnd())) {
                    peroid.setEnd(appointment.getStart().toLocalTime());
                }
                if (appointment.getStart().toLocalTime().isAfter(peroid.getStart()) && appointment.getEnd().toLocalTime().isBefore(peroid.getEnd())) {
                    toAdd.add(new TimePeroid(peroid.getStart(), appointment.getStart().toLocalTime()));
                    peroid.setStart(appointment.getEnd().toLocalTime());
                }
            }
        }
        peroids.addAll(toAdd);
        Collections.sort(peroids);
        return peroids;
    }
    
    @Override
    public List<TimePeroid> calculateAvailableHours(List<TimePeroid> availableTimePeroids, Packages packages) {
        ArrayList<TimePeroid> availableHours = new ArrayList();
        for (TimePeroid peroid : availableTimePeroids) {
            TimePeroid workPeroid = new TimePeroid(peroid.getStart(), peroid.getStart().plusMinutes(packages.getDuration()));
            while (workPeroid.getEnd().isBefore(peroid.getEnd()) || workPeroid.getEnd().equals(peroid.getEnd())) {
                availableHours.add(new TimePeroid(workPeroid.getStart(), workPeroid.getStart().plusMinutes(packages.getDuration())));
                workPeroid.setStart(workPeroid.getStart().plusMinutes(packages.getDuration()));
                workPeroid.setEnd(workPeroid.getEnd().plusMinutes(packages.getDuration()));
            }
        }
        return availableHours;
    }
    
    @Override
    public boolean isAvailable(int packagesId, int doctorId, int patientId, LocalDateTime start) {
        Packages packages = packagesService.getPackagesById(packagesId);
        TimePeroid timePeroid = new TimePeroid(start.toLocalTime(), start.toLocalTime().plusMinutes(packages.getDuration()));
        return getAvailableHours(doctorId, patientId, packagesId, start.toLocalDate()).contains(timePeroid);
    }
}
