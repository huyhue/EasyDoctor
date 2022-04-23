package fpt.edu.vn.controller;

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
//		model.addAttribute("user", userService.getUserById(currentUser.getId()));
		return "doctors/doctorList";
	}
	
	@GetMapping("/{id}")
    public String showDoctorDetails(@PathVariable("id") int DoctorId, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
          return "users/updateUserForm";

    }

    @PostMapping("/update/profile")
    public String processDoctorUpdate() {
        return "redirect:/Doctors/" + "";
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
