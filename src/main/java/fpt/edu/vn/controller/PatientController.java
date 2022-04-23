package fpt.edu.vn.controller;

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

import fpt.edu.vn.model.User;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/patients")
public class PatientController {

	private final UserService userService;
	private final EmailService emailService;

	public PatientController(UserService userService, EmailService emailService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
	}

	@GetMapping("/all")
	public String showHomePatient(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
//		model.addAttribute("user", userService.getUserById(currentUser.getId()));
		return "patients/patientList";
	}
	
	@GetMapping("/{id}")
    public String showPatientDetails(@PathVariable("id") int PatientId, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
          return "users/updateUserForm";

    }

    @PostMapping("/update/profile")
    public String processPatientUpdate() {
        return "redirect:/Patients/" + "";
    }

    @GetMapping("/new")
    public String showPatientRegistrationForm(Model model) {

        return "users/";
    }

    @PostMapping("/new")
    public String processPatientRegistrationForm() {

        return "redirect:/Patients/all";
    }

    @PostMapping("/delete")
    public String processDeletePatientRequest(@RequestParam("PatientId") int PatientId) {

        return "redirect:/Patients/all";
    }
	
}
