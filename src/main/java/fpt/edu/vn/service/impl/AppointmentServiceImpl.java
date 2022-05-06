package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.Appointment;
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
	
//	@Override
//    public List<TimePeroid> getAvailableHours(int doctorId, int customerId, int workId, LocalDate date) {
//        Provider p = userService.getProviderById(providerId);
//        WorkingPlan workingPlan = p.getWorkingPlan();
//        DayPlan selectedDay = workingPlan.getDay(date.getDayOfWeek().toString().toLowerCase());
//
//        List<Appointment> providerAppointments = getAppointmentsByProviderAtDay(providerId, date);
//        List<Appointment> customerAppointments = getAppointmentsByCustomerAtDay(customerId, date);
//
//        List<TimePeroid> availablePeroids = selectedDay.getTimePeroidsWithBreaksExcluded();
//        availablePeroids = excludeAppointmentsFromTimePeroids(availablePeroids, providerAppointments);
//
//        availablePeroids = excludeAppointmentsFromTimePeroids(availablePeroids, customerAppointments);
//        return calculateAvailableHours(availablePeroids, workService.getWorkById(workId));
//    }
}
