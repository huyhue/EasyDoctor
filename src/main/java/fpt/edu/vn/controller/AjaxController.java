package fpt.edu.vn.controller;

import com.google.common.collect.Lists;

import fpt.edu.vn.component.AppointmentRegisterForm;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.NotificationService;
import fpt.edu.vn.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class AjaxController {

	private final AppointmentService appointmentService;
	private final NotificationService notificationService;
	private final UserService userService;

	public AjaxController(AppointmentService appointmentService, NotificationService notificationService,
			UserService userService) {
		super();
		this.appointmentService = appointmentService;
		this.notificationService = notificationService;
		this.userService = userService;
	}

	@GetMapping("/user/{userId}/appointments")
	public List<Appointment> getAppointmentsForUser(@PathVariable("userId") int userId,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		if (currentUser.hasRole("ROLE_DOCTOR")) {
			return appointmentService.getAppointmentByDoctorId(userId);
		} else
			return Lists.newArrayList();
	}

	/// api/availableHours/2/2/2022-04-03
	@GetMapping("/availableHours/{doctorId}/{packagesId}/{date}")
	public List<AppointmentRegisterForm> getAvailableHours(@PathVariable("doctorId") int doctorId,
			@PathVariable("packagesId") int packagesId, @PathVariable("date") String date,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		LocalDate localDate = LocalDate.parse(date);
		return appointmentService.getAvailableHours(doctorId, currentUser.getId(), packagesId, localDate).stream()
				.map(timePeriod -> new AppointmentRegisterForm(packagesId, doctorId,
						timePeriod.getStart().atDate(localDate), timePeriod.getEnd().atDate(localDate)))
				.collect(Collectors.toList());
	}

	@GetMapping("/user/notifications")
	public int getUnreadNotificationsCount(@AuthenticationPrincipal CustomUserDetails currentUser) {
		return notificationService.getUnreadNotifications(currentUser.getId()).size();
	}
	
}
