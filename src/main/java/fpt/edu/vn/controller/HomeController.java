package fpt.edu.vn.controller;

import java.util.UUID;

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
public class HomeController {

	private final UserService userService;
	private final EmailService emailService;

	public HomeController(UserService userService, EmailService emailService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
	}

	@GetMapping("/")
	public String showHome(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", userService.getUserById(currentUser.getId()));
		return "home";
	}

	@GetMapping("/login")
	public String login(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		if (currentUser != null) {
			return "redirect:/";
		}
		return "users/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(@ModelAttribute("user") User user) {
		return "users/registerPatient";
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String showForgotPassword(@ModelAttribute("user") User user) {
		return "users/forgotPassword";
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String resetForgotPassword(@ModelAttribute("user") User user, Model model) {
//		do something
		return "users/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addPatientRegistration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "users/registerPatient";
		}
		// Check user exist
		Boolean userExists = userService.checkUserExists(user.getEmail(), user.getUserName());

		if (userExists == true) {
			model.addAttribute("alreadyRegisteredMessage",
					"Oops!  There is already a user registered.");
			return "users/registerPatient";
		} else {
			user.setConfirmationToken(UUID.randomUUID().toString());
			userService.saveRegister(user);
			emailService.sendConfirmRegistration(user);
			model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
		}
		return "users/registerPatient";
	}

	// Process confirmation link
	@RequestMapping(value = "/register/confirm", method = RequestMethod.GET)
	public String confirmRegistration(Model model, @RequestParam("token") String token) {
		User user = userService.findByConfirmationToken(token);

		if (user == null) { 
			// No token found in DB
			model.addAttribute("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { 
			// Token found
			model.addAttribute("user", user);
		}

		return "users/confirm";
	}

	// Process confirmation link
	@RequestMapping(value = "/register/confirm", method = RequestMethod.POST)
	public String confirmRegistration(Model model, @ModelAttribute("user") User user) {

		User userDR = userService.findByConfirmationToken(user.getConfirmationToken());
		if(userDR != null) {
			userService.savePasswordByUser(user);
			model.addAttribute("successMessage", "Your password has been set!");
		} else {
			model.addAttribute("errorMessage", "Your password hasn't been set. Please again!");
		}
		return "users/login";
	}

	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		return "access-denied";
	}

}
