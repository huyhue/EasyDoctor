package fpt.edu.vn.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.History;
import fpt.edu.vn.model.WorkingPlan;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;
import fpt.edu.vn.service.WorkingPlanService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

	private final UserService userService;
	private final EmailService emailService;
	private final PackagesService packagesService;
	private final WorkingPlanService workingPlanService;
	private final AppointmentService appointmentService;

	public DoctorController(UserService userService, EmailService emailService, PackagesService packagesService,
			WorkingPlanService workingPlanService, AppointmentService appointmentService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
		this.packagesService = packagesService;
		this.workingPlanService = workingPlanService;
		this.appointmentService = appointmentService;
	}

	@GetMapping("/home")
	public String showHomeDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", userService.getUserById(currentUser.getId()));
		return "doctors/home";
	}

	@GetMapping("/all")
	public String showAllDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("doctors", userService.getAllDoctorsByPatient());
		model.addAttribute("specialties", userService.getAllSpecialty());
		Set<Doctor> recentDoctors = new HashSet<>();

		List<Appointment> list = appointmentService.getAppointmentByPatientId(currentUser.getId());
		for (Appointment appointment : list) {
			recentDoctors.add(appointment.getDoctor());
		}
		model.addAttribute("recentDoctors", recentDoctors);
		model.addAttribute("declaration", userService.getDeclarationByPatientId(currentUser.getId()));
		return "doctors/doctorList";
	}

	@GetMapping("/{id}")
	public String showDoctorDetails(@PathVariable("id") int doctorId, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		Doctor doctor = userService.getDoctorById(doctorId);
		if (currentUser.getId() == doctorId || currentUser.hasRole("ROLE_ADMIN")) {
			if (!model.containsAttribute("user")) {
				model.addAttribute("user", doctor);
			}
			if (!model.containsAttribute("passwordChange")) {
				model.addAttribute("passwordChange", new ChangePasswordForm(doctorId));
			}
			model.addAttribute("certification", userService.getCertificationByUserId(doctorId));
			model.addAttribute("account_type", "doctors");
			model.addAttribute("allPackages", packagesService.getAllPackages());
			model.addAttribute("formActionProfile", "/doctors/update/profile");
			model.addAttribute("formActionPassword", "/doctors/update/password");

			model.addAttribute("numberScheduled", appointmentService.getNumberScheduledAppointmentByUserId(doctorId));
			model.addAttribute("numberCanceled", appointmentService.getNumberCanceledAppointmentByUserId(doctorId));
			return "users/updateUserForm";
		} else {
			throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
		}
	}

	@PostMapping("/update/profile")
	public String processDoctorUpdate(@ModelAttribute("user") Doctor user, BindingResult bindingResult) {
//    	if (bindingResult.hasErrors()) {
//			return "redirect:/doctors/" + user.getId();
//		}
		userService.updateDoctor(user);
		return "redirect:/doctors/" + user.getId();
	}

	@PostMapping("/update/password")
	public String processProviderPasswordUpate(@ModelAttribute("passwordChange") ChangePasswordForm passwordChange,
			BindingResult bindingResult) {
		userService.updateUserPassword(passwordChange);
		return "redirect:/doctors/" + passwordChange.getId();
	}

	@GetMapping("/availability")
	public String showAvailability(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("plan", workingPlanService.getWorkingPlanByDoctorId(currentUser.getId()));
		model.addAttribute("breakModel", new TimePeroid());
		return "doctors/doctorAvailability";
	}

	@PostMapping("/availability")
	public String processWorkingPlanUpdate(@ModelAttribute("plan") WorkingPlan plan) {
		workingPlanService.updateWorkingPlan(plan);
		return "redirect:/doctors/availability";
	}

	// http://localhost:8080/providers/availability/breakes/add
	@PostMapping("/availability/breakes/add")
	public String processAddBreak(@ModelAttribute("breakModel") TimePeroid breakToAdd,
			@RequestParam("planId") int planId, @RequestParam("dayOfWeek") String dayOfWeek) {
//        System.out.print("Test: "+breakToAdd+planId+dayOfWeek);
		workingPlanService.addBreakToWorkingPlan(breakToAdd, planId, dayOfWeek);
		return "redirect:/doctors/availability";
	}

	@PostMapping("/availability/breakes/delete")
	public String processDeleteBreak(@ModelAttribute("breakModel") TimePeroid breakToDelete,
			@RequestParam("planId") int planId, @RequestParam("dayOfWeek") String dayOfWeek) {
		workingPlanService.deleteBreakFromWorkingPlan(breakToDelete, planId, dayOfWeek);
		return "redirect:/doctors/availability";
	}

	@RequestMapping(value = "/saveResult", method = RequestMethod.POST)
	public String saveResultByDoctor(@ModelAttribute("history") History history, @RequestParam("files") MultipartFile[] files,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		history.setPulished(true);
		history.setDoctor(userService.getUserById(currentUser.getId()).getFullname());
		history.setUpdatedAt(LocalDateTime.now());
		userService.saveResultByDoctor(history, files);
		return "redirect:/recordMedical/" + history.getPatient().getId();
	}

	@RequestMapping(value = "/saveDraftResult", method = RequestMethod.POST)
	public String saveDraftResultByDoctor(@ModelAttribute("history") History history, @RequestParam("files") MultipartFile[] files,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		history.setDoctor(userService.getUserById(currentUser.getId()).getFullname());
		history.setUpdatedAt(LocalDateTime.now());
		userService.saveResultByDoctor(history, files);
		return "redirect:/recordMedical/" + history.getPatient().getId();
	}

}
