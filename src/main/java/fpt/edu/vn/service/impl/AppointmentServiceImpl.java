package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import fpt.edu.vn.component.ChatMessage;
import fpt.edu.vn.component.DayPlan;
import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.exception.AppointmentNotFoundException;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.AppointmentStatus;
import fpt.edu.vn.model.Message;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.User;
import fpt.edu.vn.model.WorkingPlan;
import fpt.edu.vn.repository.AppointmentRepository;
import fpt.edu.vn.repository.MessageRepository;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.NotificationService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;
    private final JwtTokenServiceImpl jwtTokenService;

	public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService,
			PackagesService packagesService, MessageRepository messageRepository,
			NotificationService notificationService, JwtTokenServiceImpl jwtTokenService) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.userService = userService;
		this.packagesService = packagesService;
		this.messageRepository = messageRepository;
		this.notificationService = notificationService;
		this.jwtTokenService = jwtTokenService;
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
    @PreAuthorize("#patientId == principal.id")
    public List<Appointment> getAppointmentByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId);
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
    
    @Override
    public void createNewAppointment(int packagesId, int doctorId, int patientId, LocalDateTime start) {
        if (isAvailable(packagesId, doctorId, patientId, start)) {
            Appointment appointment = new Appointment();
            appointment.setStatus(AppointmentStatus.SCHEDULED);
            appointment.setPatient(userService.getPatientById(patientId));
            appointment.setDoctor(userService.getDoctorById(doctorId));
            Packages packages = packagesService.getPackagesById(packagesId);
            appointment.setPackages(packages);
            appointment.setStart(start);
            appointment.setEnd(start.plusMinutes(packages.getDuration()));
            appointmentRepository.save(appointment);
            notificationService.newAppointmentNotification(appointment, true);
        } else {
            throw new RuntimeException();
        }
    }
    
    @Override
    public void cancelUserAppointmentById(int appointmentId, int userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        if (appointment.getPatient().getId() == userId || appointment.getDoctor().getId() == userId) {
            appointment.setStatus(AppointmentStatus.CANCELED);
            User canceler = userService.getUserById(userId);
            appointment.setCanceler(canceler);
            appointment.setCanceledAt(LocalDateTime.now());
            appointmentRepository.save(appointment);
            if (canceler.equals(appointment.getPatient())) {
                notificationService.appointmentCanceledByPatient(appointment, true);
            } else if (canceler.equals(appointment.getDoctor())) {
                notificationService.appointmentCanceledByDoctor(appointment, true);
            }
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }
    
    @Override
    @PostAuthorize("returnObject.doctor.id == principal.id or returnObject.patient.id == principal.id or hasRole('ADMIN') ")
    public Appointment getAppointmentByIdWithAuthorization(int id) {
        return getAppointmentById(id);
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
    }
    
    @Override
    public String getCancelNotAllowedReason(int userId, int appointmentId) {
        User user = userService.getUserById(userId);
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);

        if (user.hasRole("ROLE_ADMIN")) {
            return "Only patient or doctor can cancel appointments";
        }

        if (appointment.getDoctor().equals(user)) {
            if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
                return "Only appoinmtents with scheduled status can be cancelled.";
            } else {
                return null;
            }
        }

        if (appointment.getPatient().equals(user)) {
            if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
                return "Only appoinmtents with scheduled status can be cancelled.";
            } else if (LocalDateTime.now().plusDays(1).isAfter(appointment.getStart())) {
                return "Appointments which will be in less than 24 hours cannot be canceled.";
            } else if (!appointment.getPackages().getEditable()) {
                return "This type of appointment can be canceled only by Doctor.";
            } else if (getCanceledAppointmentsByPatientIdForCurrentMonth(userId).size() >= NUMBER_OF_ALLOWED_CANCELATIONS_PER_MONTH) {
                return "You can't cancel this appointment because you exceeded maximum number of cancellations in this month.";
            } else {
                return null;
            }
        }
        return null;
    }
    
    @Override
    public List<Appointment> getCanceledAppointmentsByPatientIdForCurrentMonth(int patientId) {
        return appointmentRepository.findByPatientIdCanceledAfterDate(patientId, LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
    }
    
    @Override
    public boolean isPatientAllowedToRejectAppointment(int userId, int appointmentId) {
        User user = userService.getUserById(userId);
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);

        return appointment.getPatient().equals(user) && appointment.getStatus().equals(AppointmentStatus.FINISHED) && !LocalDateTime.now().isAfter(appointment.getEnd().plusDays(1));
    }
    
    @Override
    public boolean isPatientAllowedToReview(int userId, int appointmentId) {
    	User user = userService.getUserById(userId);
    	Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
    	
    	return appointment.getPatient().equals(user) && appointment.getStatus().equals(AppointmentStatus.INVOICED);
    }
    
    @Override
    public boolean isDoctorAllowedToAcceptRejection(int doctorId, int appointmentId) {
        User user = userService.getUserById(doctorId);
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);

        return appointment.getDoctor().equals(user) && appointment.getStatus().equals(AppointmentStatus.REJECTION_REQUESTED);
    }
    
    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
    
    @Override
    public boolean requestAppointmentRejection(int appointmentId, int patientId) {
        if (isPatientAllowedToRejectAppointment(patientId, appointmentId)) {
            Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
            appointment.setStatus(AppointmentStatus.REJECTION_REQUESTED);
            notificationService.newAppointmentRejectionRequestedNotification(appointment, true);
            updateAppointment(appointment);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean requestAppointmentRejection(String token) {
        if (jwtTokenService.validateToken(token)) {
            int appointmentId = jwtTokenService.getAppointmentIdFromToken(token);
            int patientId = jwtTokenService.getPatientIdFromToken(token);
            return requestAppointmentRejection(appointmentId, patientId);
        }
        return false;
    }
    
    @Override
    public boolean acceptRejection(int appointmentId, int doctorId) {
        if (isDoctorAllowedToAcceptRejection(doctorId, appointmentId)) {
            Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
            appointment.setStatus(AppointmentStatus.REJECTED);
            updateAppointment(appointment);
            notificationService.newAppointmentRejectionAcceptedNotification(appointment, true);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean acceptRejection(String token) {
        if (jwtTokenService.validateToken(token)) {
            int appointmentId = jwtTokenService.getAppointmentIdFromToken(token);
            int doctorId = jwtTokenService.getDoctorIdFromToken(token);
            return acceptRejection(appointmentId, doctorId);
        }
        return false;
    }
    
    @Override
    public List<ChatMessage> getMessagesByAppointmentId(int appointmentId) {
    	List<ChatMessage> list = new ArrayList<>();
		List<Message> messageslist = messageRepository.findByAppointmentId(appointmentId);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    	
		for (Message me : messageslist) {
			list.add(new ChatMessage(me.getMessage(), me.getAuthor().getFullname(), me.getAuthor().getNameRole(), me.getCreatedAt().format(formatter))); 
		}
        return list;
    }
    
    @Override
    public void addMessageToAppointmentChat(int appointmentId, int authorId, Message chatMessage) {
        Appointment appointment = getAppointmentByIdWithAuthorization(appointmentId);
        if (appointment.getDoctor().getId() == authorId || appointment.getPatient().getId() == authorId) {
            chatMessage.setAuthor(userService.getUserById(authorId));
            chatMessage.setAppointment(appointment);
            chatMessage.setCreatedAt(LocalDateTime.now());
            messageRepository.save(chatMessage);
//            notificationService.newChatMessageNotification(chatMessage, true);
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }
    
    @Override
    public void updateUserAppointmentsStatuses(int userId) {
        for (Appointment appointment : appointmentRepository.findScheduledByUserIdWithEndBeforeDate(LocalDateTime.now(), userId)) {
            appointment.setStatus(AppointmentStatus.FINISHED);
            updateAppointment(appointment);
        }

        for (Appointment appointment : appointmentRepository.findFinishedByUserIdWithEndBeforeDate(LocalDateTime.now().minusDays(1), userId)) {
            appointment.setStatus(AppointmentStatus.INVOICED);
            updateAppointment(appointment);
        }
    }

    @Override
    public void updateAllAppointmentsStatuses() {
        appointmentRepository.findScheduledWithEndBeforeDate(LocalDateTime.now())
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatus.FINISHED);
                    updateAppointment(appointment);
                    if (LocalDateTime.now().minusDays(1).isBefore(appointment.getEnd())) {
                        notificationService.newAppointmentFinishedNotification(appointment, true);
                    }
                });

        appointmentRepository.findFinishedWithEndBeforeDate(LocalDateTime.now().minusDays(1))
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatus.CONFIRMED);
                    updateAppointment(appointment);
                });
    }
    
    @Override
    public List<Appointment> getConfirmedAppointmentsByPatientId(int patientId) {
        return appointmentRepository.findConfirmedByPatientId(patientId);
    }
}
