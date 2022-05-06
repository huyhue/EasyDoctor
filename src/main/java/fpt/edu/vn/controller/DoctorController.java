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
import fpt.edu.vn.component.UserForm;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.User;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

	private final UserService userService;
	private final EmailService emailService;
	private final PackagesService packagesService;

	public DoctorController(UserService userService, EmailService emailService, PackagesService packagesService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
		this.packagesService = packagesService;
	}

	@GetMapping("/home")
	public String showHomeDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", userService.getUserById(currentUser.getId()));
		return "doctors/home";
	}
	
	@GetMapping("/all")
	public String showAllDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("doctors", userService.getAllDoctorsByPatient());
		return "doctors/doctorList";
	}
	
	@GetMapping("/{id}")
    public String showDoctorDetails(@PathVariable("id") int doctorId, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		Doctor doctor = userService.getDoctorById(doctorId);
		if (currentUser.getId() == doctorId || currentUser.hasRole("ROLE_ADMIN")) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", doctor);
            }
            if (!model.containsAttribute("passwordChange")) {
                model.addAttribute("passwordChange", new ChangePasswordForm(doctorId));
            }
            model.addAttribute("account_type", "doctors");
            model.addAttribute("allPackages", packagesService.getAllPackages());
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
    public String processDoctorUpdate(@ModelAttribute("user") Doctor user, BindingResult bindingResult) {
//    	if (bindingResult.hasErrors()) {
//			return "redirect:/doctors/" + user.getId();
//		}
    	userService.updateDoctor(user);
        return "redirect:/doctors/" + user.getId();
    }
    
    @PostMapping("/update/password")
    public String processProviderPasswordUpate(@ModelAttribute("passwordChange") ChangePasswordForm passwordChange, BindingResult bindingResult) {
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
