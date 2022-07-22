package fpt.edu.vn.controller;

import java.security.Principal;


import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
<<<<<<< HEAD

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.AppoinmentDto;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.component.ReviewDto;
import fpt.edu.vn.model.Clinic;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.Review;
=======
import fpt.edu.vn.model.User;
import fpt.edu.vn.component.CommonMsg;
import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.component.PatientDto;
import fpt.edu.vn.model.Clinic;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.Profile;
//import fpt.edu.vn.model.ViewPatient;
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.PatientService;
//import fpt.edu.vn.service.PatientService;
import fpt.edu.vn.service.AppointmentService;
<<<<<<< HEAD

=======
import fpt.edu.vn.service.ClinicService;
import fpt.edu.vn.service.DoctorService;
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
import fpt.edu.vn.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final PackagesService packagesService;
	private final AppointmentService appointmentService;
	private final EmailService emailService;
	private final DoctorService doctorService;
	private final ClinicService clinicService;
	private final PatientService patientService;

	public AdminController(UserService userService, PackagesService packagesService,
			AppointmentService appointmentService, EmailService emailService,
			DoctorService doctorService, ClinicService clinicService,
			PatientService patientService) {
		super();
		this.userService = userService;
		this.packagesService = packagesService;
		this.appointmentService = appointmentService;
		this.emailService = emailService;
		this.doctorService = doctorService;
		this.clinicService = clinicService;
		this.patientService = patientService;
		
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
	
	//profile
	@GetMapping("/profile")
	public String adminProfile(Model model, Principal principal) {
		if (principal != null) {
			User getUser = userService.findByUserName(principal.getName());
			model.addAttribute("userProfile", getUser);
		} else {
			model.addAttribute("userProfile", new User());
			return "login";
		}
		return "admin/profile";
	}
	
	@PostMapping("/updateProfile")
	@ResponseBody
	public CommonMsg updateProfile(@RequestBody Profile profile) {
		return userService.updateProfileInfo(profile);
	}
	
	//Packages
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
<<<<<<< HEAD

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
		return userService.saveDoctor(doctordto);
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
=======
	
	//Doctor
	@GetMapping("/doctor")
	public String viewDoctor(Model model) {
		return "admin/adddoctor";
	}
	
	@GetMapping(value = "/getListDoctor")
	@ResponseBody 
	public List<DoctorDto> viewDoctorList(){
		return doctorService.getAllDoctor();
	}
	
	@PostMapping("saveDoctor")
	@ResponseBody
	public CommonMsg saveDoctor(@RequestBody DoctorDto doctordto) {
		return doctorService.saveDoctor(doctordto);
	}
	
	@GetMapping(value = "/deleteDoctor")
	@ResponseBody
	public CommonMsg deleteDoctor(@RequestParam("id") int id) {
		return doctorService.deleteDoctor(id);
	}
	
	//Clinic
	@GetMapping("/clinic")
	public String viewClinic(Model model) {
		return "admin/clinic";
	}
	
	@GetMapping(value = "/getListClinic")
	@ResponseBody 
	public List<Clinic> viewClinicList(){
		return clinicService.getAllClinic();
	}
	
	@PostMapping("/saveClinic")
	@ResponseBody
	public CommonMsg saveClinic(@RequestBody Clinic clinic) {
		return clinicService.saveClinic(clinic); 
	}
	
	@GetMapping(value = "/deleteClinic")
	@ResponseBody
	public CommonMsg deleteClinic(@RequestParam("id") int id) {
		return clinicService.deleClinic(id);
	}
	
//	Patient
	
	@GetMapping("/viewPatient")
	public String viewPatient(Model model) {
		return "admin/patient";
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
	}
	
	@GetMapping(value = "/getListPatient")
	@ResponseBody
	public List<PatientDto> viewPatientList() {
<<<<<<< HEAD
		return userService.getAllPatient();
	}
	
	@PostMapping(value = "/savePatient")
	@ResponseBody
	public CommonMsg savePatient(@RequestBody PatientDto patientdto) {
		return userService.savePatient(patientdto);
=======
		return patientService.getAllPatient();
	}
	
	@GetMapping(value = "/savePatient")
	@ResponseBody
	public CommonMsg savePatient(@RequestBody PatientDto patientdto) {
		return patientService.savePatient(patientdto);
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
	}
	
	@GetMapping(value = "/deletePatient")
	@ResponseBody
	public CommonMsg deletePatient(@RequestParam("id") int id) {
<<<<<<< HEAD
		return userService.deletePatient(id);
	}
	
	//Appointment
	@GetMapping("/viewApp")
	public String viewAppoinmentList(Model model) {
		return "admin/viewApp";
	}
	
	@GetMapping(value = "/getviewAppList")
	@ResponseBody
	public List<AppoinmentDto> viewAppointmentList(){
		return appointmentService.getAllAppointment();
	}
	
	@GetMapping(value = "/deleteviewApp")
	@ResponseBody
	public CommonMsg deleteAppoinment(@RequestParam("id") int id) {
		return appointmentService.deleteAppoinment(id);
	}
	
//	//Review
	@GetMapping("/viewReview")
	public String viewReviewList(Model model) {
		return "admin/viewReview";
	}
	
	@GetMapping(value = "/getviewReviewList")
	@ResponseBody
	public List<ReviewDto> viewReviewList(){
		return userService.getAllReview();
	}
	@GetMapping(value = "/deleteviewReview")
	@ResponseBody
	public CommonMsg deleteReview(@RequestParam("id") int id) {
		return userService.deleteReview(id);
=======
		return patientService.deletePatient(id);
>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
	}
	
	
	
<<<<<<< HEAD
=======

>>>>>>> 26e60583d669cde20019c63deb29f1b25015c4bc
}
