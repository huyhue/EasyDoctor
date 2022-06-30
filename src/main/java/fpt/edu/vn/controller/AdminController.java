package fpt.edu.vn.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final PackagesService packagesService;
	private final AppointmentService appointmentService;
	private final EmailService emailService;

	public AdminController(UserService userService, PackagesService packagesService,
			AppointmentService appointmentService, EmailService emailService) {
		super();
		this.userService = userService;
		this.packagesService = packagesService;
		this.appointmentService = appointmentService;
		this.emailService = emailService;
	}

//	Redirect Page
	@GetMapping("/home")
	public String showHome(Model model) {
		model.addAttribute("totalPatient", userService.getAllPatients().size());
		model.addAttribute("totalDoctor", userService.getAllDoctors().size());
		model.addAttribute("totalPost", 5);
		model.addAttribute("totalAppointment", appointmentService.getAllAppointments().size());
		return "admin/home";
	}

	@GetMapping("/packages")
	public String viewPackage(Model model) {
		return "admin/packages";
	}

//	Handle Ajax
	@GetMapping(value = "/getListPackages")
	@ResponseBody
	public List<Packages> viewPackagesList() {
		return packagesService.getAllPackages();
	}

	@PostMapping("/savePackages")
	@ResponseBody
	public CommonMsg savePackages(@RequestBody Packages packages) {
		return packagesService.savePackages(packages);
	}

	@GetMapping(value = "/deletePackages")
	@ResponseBody
	public CommonMsg deletePackages(@RequestParam("id") int id) {
		return packagesService.deletePackages(id);
	}
}
