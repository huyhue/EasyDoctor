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

import fpt.edu.vn.component.AppoinmentDto;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.component.ReviewDto;
import fpt.edu.vn.model.Clinic;
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

	@GetMapping("/home")
	public String showHome(Model model) {
		model.addAttribute("totalPatient", userService.getAllPatients().size());
		model.addAttribute("totalDoctor", userService.getAllDoctors().size());
		model.addAttribute("totalPost", 5);
		model.addAttribute("totalAppointment", appointmentService.getAllAppointments().size());
		return "admin/home";
	}

	// Packages
	@GetMapping("/packages")
	public String viewPackage(Model model) {
		return "admin/packages";
	}

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

	// Doctor
	@GetMapping("/doctor")
	public String viewDoctor(Model model) {
		model.addAttribute("specialties", userService.getAllSpecialty());
		model.addAttribute("clinics", userService.getAllClinic());
		return "admin/doctors";
	}

	@GetMapping(value = "/getListDoctor")
	@ResponseBody
	public List<DoctorDto> viewDoctorList() {
		return userService.getAllDoctors();
	}

	@PostMapping("saveDoctor")
	@ResponseBody
	public CommonMsg saveDoctor(@RequestBody DoctorDto doctordto) {
		CommonMsg commonMsg = userService.saveDoctor(doctordto);
		// email add new doctor
		if (commonMsg.getMsgCode() == "200") {
			emailService.sendInfoNewDoctor(doctordto);
		}
		return commonMsg;
	}

	@GetMapping(value = "/deleteDoctor")
	@ResponseBody
	public CommonMsg deleteDoctor(@RequestParam("id") int id) {
		return userService.deleteDoctor(id);
	}

	// Clinic
	@GetMapping("/clinic")
	public String viewClinic(Model model) {
		return "admin/clinics";
	}

	@GetMapping(value = "/getListClinic")
	@ResponseBody
	public List<Clinic> viewClinicList() {
		return userService.getAllClinic();
	}

	@PostMapping("/saveClinic")
	@ResponseBody
	public CommonMsg saveClinic(@RequestBody Clinic clinic) {
		return userService.saveClinic(clinic);
	}

	@GetMapping(value = "/deleteClinic")
	@ResponseBody
	public CommonMsg deleteClinic(@RequestParam("id") int id) {
		return userService.deleteClinic(id);
	}

//	Patient
	@GetMapping("/viewPatient")
	public String viewPatient(Model model) {
		return "admin/patients";
	}

	@GetMapping(value = "/getListPatient")
	@ResponseBody
	public List<PatientDto> viewPatientList() {
		return userService.getAllPatient();
	}

	@PostMapping(value = "/savePatient")
	@ResponseBody
	public CommonMsg savePatient(@RequestBody PatientDto patientdto) {
		return userService.savePatient(patientdto);
	}

	@GetMapping(value = "/deletePatient")
	@ResponseBody
	public CommonMsg deletePatient(@RequestParam("id") int id) {
		return userService.deletePatient(id);
	}

	// Appointment
	@GetMapping("/viewAppointment")
	public String viewAppoinmentList(Model model) {
		return "admin/appointments";
	}

	@GetMapping(value = "/getAppointmentList")
	@ResponseBody
	public List<AppoinmentDto> viewAppointmentList() {
		return appointmentService.getAllAppointment();
	}

	@GetMapping(value = "/deleteAppoinment")
	@ResponseBody
	public CommonMsg deleteAppoinment(@RequestParam("id") int id) {
		return appointmentService.deleteAppoinment(id);
	}

	// Review
	@GetMapping("/viewReview")
	public String viewReviewList(Model model) {
		return "admin/reviews";
	}

	@GetMapping(value = "/getReviewList")
	@ResponseBody
	public List<ReviewDto> viewReviewList() {
		return userService.getAllReview();
	}

	@GetMapping(value = "/deleteReview")
	@ResponseBody
	public CommonMsg deleteReview(@RequestParam("id") int id) {
		return userService.deleteReview(id);
	}
	
}
