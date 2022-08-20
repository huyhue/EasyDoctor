package fpt.edu.vn.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import fpt.edu.vn.component.PostDTO;
import fpt.edu.vn.component.ProfileDto;
import fpt.edu.vn.component.ReviewDto;
import fpt.edu.vn.model.Clinic;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Packages;
import fpt.edu.vn.model.Question;
import fpt.edu.vn.model.User;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.InvoiceService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.UserService;
import fpt.edu.vn.service.impl.PostService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final PackagesService packagesService;
	private final AppointmentService appointmentService;
	private final InvoiceService invoiceService;
	private final EmailService emailService;
	@Autowired
	private PostService postService;
	@Autowired
    private ApplicationContext applicationContext;

	public AdminController(UserService userService, PackagesService packagesService,
			AppointmentService appointmentService, InvoiceService invoiceService, EmailService emailService) {
		super();
		this.userService = userService;
		this.packagesService = packagesService;
		this.appointmentService = appointmentService;
		this.invoiceService = invoiceService;
		this.emailService = emailService;
	}

	@GetMapping("/home")
	public String showHome(Model model) {
		model.addAttribute("totalPatient", userService.getAllPatients().size());
		model.addAttribute("totalDoctor", userService.getAllDoctors().size());
		model.addAttribute("totalAppointment", appointmentService.getAllAppointments().size());
		model.addAttribute("totalPost", postService.getAllForum().size());
		
		Map<Integer, Integer> dataAppointment = new LinkedHashMap<>();
		Map<Integer, Integer> dataPost = new LinkedHashMap<>();
		for (int i = 1; i < 13; i++) {
			dataAppointment.put(i, appointmentService.countAllAppointmentByMonth(i));
			dataPost.put(i, postService.countAllPostByMonth(i));
		}
   
        model.addAttribute("dataAppointment", dataAppointment.values());
        model.addAttribute("dataPost", dataPost.values());
		return "admin/home";
	}

	// Profile
	@GetMapping("/profile")
	public String adminProfile(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		if (currentUser != null) {
			User getUser = userService.findByUserName(currentUser.getUsername());
			model.addAttribute("userProfile", getUser);
		} else {
			model.addAttribute("userProfile", new User());
			return "login";
		}
		return "admin/profile";
	}

	@PostMapping("/saveProfile")
	@ResponseBody
	public CommonMsg saveProfile(@RequestBody ProfileDto profileDto) {
		return userService.saveProfileInfo(profileDto);
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
		CommonMsg commonMsg = userService.savePatient(patientdto);
		// email add new patient
		if (commonMsg.getMsgCode() == "200") {
			emailService.sendInfoNewPatient(patientdto);
		}
		return commonMsg;
	}

	// Appointment
	@GetMapping("/viewAppointment")
	public String viewAppoinmentList(Model model) {
		model.addAttribute("appointmentList", appointmentService.getAllAppointment());
		return "admin/appointments";
	}

	@GetMapping(value = "/getAppointmentList")
	@ResponseBody
	public List<AppoinmentDto> viewAppointmentList() {
		return appointmentService.getAllAppointment();
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

	// Question
	@GetMapping("/viewQuestion")
	public String viewQuestionList(Model model) {
		List<Question> list = userService.getAllQuestion();
		int countResponses = 0;
		for (Question question : list) {
			if (question.getResponses() == null) {
				countResponses++;
			}
		}
		model.addAttribute("countResponses", countResponses);
		return "admin/questions";
	}

	@GetMapping(value = "/getQuestionList")
	@ResponseBody
	public List<Question> viewQuestionList() {
		return userService.getAllQuestion();
	}

	@PostMapping("/sendAnswerQuestion")
	@ResponseBody
	public CommonMsg sendAnswerQuestion(@RequestBody Question q) {
		CommonMsg commonMsg = new CommonMsg();
		Question question = userService.sendAnswerQuestion(q);
		emailService.sendQuestionSuccess(question);
		commonMsg.setMsgCode("200");
		return commonMsg;
	}

	// Invoice
	@GetMapping("/viewInvoice")
	public String viewInvoiceList(Model model) {
		model.addAttribute("invoiceList", invoiceService.getAllInvoices());
		return "admin/invoices";
	}

	// Forum
	@GetMapping("/forum")
	public String viewForum(Model model) {
		model.addAttribute("specialties", userService.getAllSpecialty());
		return "admin/forums";
	}

	@GetMapping(value = "/getListForum")
	@ResponseBody
	public List<PostDTO> viewForumList() {
		return postService.getAllForum();
	}

	@PostMapping("/saveForum")
	@ResponseBody
	public CommonMsg saveForum(@RequestBody PostDTO post) throws IOException {
		return postService.saveForum(post);
	}

	@GetMapping(value = "/deleteForum")
	@ResponseBody
	public CommonMsg deleteForum(@RequestParam("id") long id) {
		return postService.deleteForum(id);
	}
	
	@GetMapping("/clinicReport")
	public String accountReport(Model model) {
		model.addAttribute("listOfClinic", userService.getAllClinic());
		return "admin/reportClinic";
	}

	@GetMapping(path = "doctorByClinicReport")
    @ResponseBody
    public void doctorByClinicReport(HttpServletResponse response,@RequestParam("id") int clinicId) throws Exception {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("logo", "static/img/logo.jpg");
        
        List<Doctor> getReportData = userService.getDoctorsByClinicId(clinicId);
        Resource resource = applicationContext.getResource("classpath:templates/reports/clinic-report.jrxml");
        InputStream inputStream = resource.getInputStream();
        JasperReport report = JasperCompileManager.compileReport(inputStream);
        JRDataSource dataSource = new JRBeanCollectionDataSource(getReportData);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
	
	@GetMapping("/appointmentReport")
	public String appointmentReport(Model model) {
		return "admin/reportAppointment";
	}
	
	@GetMapping(path = "appointmentByDateReport")
	@ResponseBody
	public void appointmentByDateReport(HttpServletResponse response, @RequestParam("start") String start, @RequestParam("end") String end) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("logo", "static/img/logo.jpg");
		
		List<AppoinmentDto> getReportData = appointmentService.getAppointmentByDate(start, end);
		
		Double totalAmount = (double) 0;
		Integer totalStatusInvoiced = 0;
		Integer totalStatusCanceled = 0;
		Integer totalStatusRejected = 0;
		params.put("totalAmount", totalAmount);
		params.put("totalStatusInvoiced", totalStatusInvoiced);
		params.put("totalStatusCanceled", totalStatusCanceled);
		params.put("totalStatusRejected", totalStatusRejected);
		
		Resource resource = applicationContext.getResource("classpath:templates/reports/appointment-report.jrxml");
		InputStream inputStream = resource.getInputStream();
		JasperReport report = JasperCompileManager.compileReport(inputStream);
		JRDataSource dataSource = new JRBeanCollectionDataSource(getReportData);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
}
