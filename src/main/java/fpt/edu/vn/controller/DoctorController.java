package fpt.edu.vn.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.model.User;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

	private final UserService userService;
	private final EmailService emailService;

	public DoctorController(UserService userService, EmailService emailService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
	}

	@GetMapping("/all")
	public String showAllDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("doctors", userService.getAllDoctorsByPatient());
		return "doctors/doctorList";
	}
	
	@GetMapping("/{id}")
    public String showDoctorDetails(@PathVariable("id") int doctorId, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		if (currentUser.getId() == doctorId || currentUser.hasRole("ROLE_ADMIN")) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", userService.getDoctorById(doctorId));
            }
            if (!model.containsAttribute("passwordChange")) {
                model.addAttribute("passwordChange", new ChangePasswordForm(doctorId));
            }
            model.addAttribute("account_type", "doctors");
            model.addAttribute("formActionProfile", "/doctors/update/profile");
            model.addAttribute("formActionPassword", "/doctors/update/password");
            model.addAttribute("numberOfScheduledAppointments", 1);
            model.addAttribute("numberOfCanceledAppointments", 2);
            return "users/updateUserForm";
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }

    @PostMapping("/update/profile")
    public String processDoctorUpdate() {
        return "redirect:/patients/" + "all";
    }
    
    @PostMapping("/update/password")
    public String processProviderPasswordUpate(@Valid @ModelAttribute("passwordChange") ChangePasswordForm passwordChange, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordChange", bindingResult);
            redirectAttributes.addFlashAttribute("passwordChange", passwordChange);
            return "redirect:/doctors/" + passwordChange.getId();
        }
        userService.updateUserPassword(passwordChange);
        return "redirect:/doctors/" + passwordChange.getId();
    }

    @GetMapping("/new")
    public String showDoctorRegistrationForm(Model model) {

        return "users/";
    }

    @PostMapping("/new")
    public String processDoctorRegistrationForm() {

        return "redirect:/Doctors/all";
    }

    @PostMapping("/delete")
    public String processDeleteDoctorRequest(@RequestParam("DoctorId") int DoctorId) {

        return "redirect:/Doctors/all";
    }

	
}
