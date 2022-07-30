package fpt.edu.vn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.model.Declaration;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/patients")
public class PatientController {

	private final UserService userService;
	private final AppointmentService appointmentService;
	private static final Logger log = LoggerFactory.getLogger(PatientController.class);

	public PatientController(UserService userService, AppointmentService appointmentService) {
		super();
		this.userService = userService;
		this.appointmentService = appointmentService;
	}

	@GetMapping("/{id}")
	public String showPatientDetails(@PathVariable("id") int patientId, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		Patient patient = userService.getPatientById(patientId);
		if (currentUser.getId() == patientId || currentUser.hasRole("ROLE_ADMIN")) {
			if (!model.containsAttribute("user")) {
				model.addAttribute("user", patient);
			}
			if (!model.containsAttribute("passwordChange")) {
				model.addAttribute("passwordChange", new ChangePasswordForm(patientId));
			}
			model.addAttribute("declaration", userService.getDeclarationByPatientId(currentUser.getId()));
			model.addAttribute("account_type", "patients");
			model.addAttribute("formActionProfile", "/patients/update/profile");
			model.addAttribute("formActionPassword", "/patients/update/password");
			model.addAttribute("numberScheduled", appointmentService.getNumberScheduledAppointmentByUserId(patientId));
			model.addAttribute("numberCanceled", appointmentService.getNumberCanceledAppointmentByUserId(patientId));
			return "users/updateUserForm";
		} else {
			throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
		}

	}

	@PostMapping("/update/profile")
	public String processPatientUpdate(@ModelAttribute("user") Patient user, Model model, BindingResult bindingResult) {
		log.info(">> Patient ");
		userService.updatePatient(user);
		return "redirect:/patients/" + user.getId();
	}

	@PostMapping("/update/password")
	@ResponseBody
	public CommonMsg processPatientPasswordUpdate(@RequestBody ChangePasswordForm passwordChange) {
		return userService.updateUserPassword(passwordChange);
	}

	@GetMapping("/follow")
	public String followDoctor(@RequestParam Integer id, @AuthenticationPrincipal CustomUserDetails currentUser) {
		userService.followDoctor(id, currentUser.getId());
		return "redirect:/detail/" + id;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public String unfollowDoctor(@RequestParam(required = false) Integer id, @AuthenticationPrincipal CustomUserDetails currentUser) {
		userService.unfollowDoctor(id, currentUser.getId());
		return "redirect:/detail/" + id;
	}

	@PostMapping("/declaration/{doctorId}")
	public String addNewDeclaration(@PathVariable Integer doctorId,
			@ModelAttribute("declaration") Declaration declaration,
			@AuthenticationPrincipal CustomUserDetails currentUser) {

		declaration.setPatient(userService.getPatientById(currentUser.getId()));
		userService.saveDeclarationByPatientId(declaration);
		return "redirect:/appointments/new/" + doctorId;
	}

	@PostMapping("/declaration/{declarationId}/{doctorId}")
	public String changeDeclaration(@PathVariable Integer declarationId, @PathVariable Integer doctorId,
			@ModelAttribute("declaration") Declaration declaration) {
		userService.updateDeclarationByPatientId(declaration);
		return "redirect:/appointments/new/" + doctorId;
	}

}
