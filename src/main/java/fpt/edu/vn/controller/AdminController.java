package fpt.edu.vn.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fpt.edu.vn.model.User;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final EmailService emailService;

	public AdminController(UserService userService, EmailService emailService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
	}
	
//	Redirect Page
	@GetMapping("/home")
	public String showHome(Model model) {
		model.addAttribute("totalUniversity", 5);
		model.addAttribute("totalContact", 5);
		model.addAttribute("totalPost",5);
		model.addAttribute("totalUrl", 5);
		return "admin/home";
	}
	
	@GetMapping("/packages")
	public String viewPackage(Model model) {
		return "admin/packages";
	}

//	Handle Ajax
	
}
