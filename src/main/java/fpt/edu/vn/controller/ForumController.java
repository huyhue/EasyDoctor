package fpt.edu.vn.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/forum")
public class ForumController {

	private final UserService userService;

	public ForumController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/list")
	public String showListPost(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("specialties", userService.getAllSpecialty());
		return "forum/home";
	}
	
}
