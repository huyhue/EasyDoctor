package fpt.edu.vn.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.FileModel;
import fpt.edu.vn.model.History;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.Review;
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
	public String showLandingPage(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		try {
			if (currentUser == null) {
				return "home";
			}
			if (currentUser.hasRole("ROLE_PATIENT")) {
				return "redirect:/doctors/all";
			} else if (currentUser.hasRole("ROLE_DOCTOR")) {
				return "redirect:/doctors/home";
			} else if (currentUser.hasRole("ROLE_ADMIN")) {
				return "redirect:/admin/home";
			}
		} catch (Exception ex) {
			return "home";
		}
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
	public String showRegistrationForm(@ModelAttribute("user") Patient user) {
		return "users/registerPatient";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String addPatientRegistration(@ModelAttribute("user") Patient user, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "users/registerPatient";
		}
		// Check user exist
		User userExists = userService.findByUserName(user.getUserName());

		if (userExists != null) {
			model.addAttribute("alreadyRegisteredMessage", "Oops!  ???? c?? ng?????i d??ng ????ng k??.");
			return "users/registerPatient";
		} else {
			user.setConfirmationToken(UUID.randomUUID().toString());
			userService.savePatientRegister(user);
			emailService.sendRegisterSuccess(user);
			model.addAttribute("successMessage", "B???n ???? ????ng k?? th??nh c??ng.");
		}
		return "users/login";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String showForgotPassword(@ModelAttribute("user") User user) {
		return "users/forgotPassword";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String resetForgotPassword(@ModelAttribute("user") User user, Model model) {
		User existUser = userService.findByEmail(user.getEmail());
		if (existUser != null) {
			emailService.sendConfirmForgotPassword(existUser);
			model.addAttribute("confirmationMessage", "Email x??c nh???n ???? ???????c g???i ?????n " + user.getEmail());
		} else {
			model.addAttribute("alreadyRegisteredMessage", "Email x??c nh???n kh??ng t???n t???i.");
		}
		return "users/forgotPassword";
	}

	// Process confirmation link
	@RequestMapping(value = "/register/confirm", method = RequestMethod.GET)
	public String confirmRegistration(Model model, @RequestParam("token") String token) {
		User user = userService.findByConfirmationToken(token);

		if (user == null) {
			// No token found in DB
			model.addAttribute("invalidToken", "Oops!  Link x??c nh???n kh??ng h???p l???.");
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
		if (userDR != null) {
			userService.savePasswordByUser(user);
			model.addAttribute("successMessage", "M???t kh???u c???a b???n ???? ???????c thay ?????i!");
		} else {
			model.addAttribute("errorMessage", "M???t kh???u c???a b???n kh??ng ???????c thay ?????i. Vui l??ng th??? l???i!");
		}
		return "users/login";
	}

	@GetMapping("/detail/{id}")
	public String showDoctorDetails(@PathVariable("id") int doctorId, ModelMap modelMap,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		Doctor doctor = userService.getDoctorById(doctorId);
		modelMap.put("doctor", doctor);
		modelMap.put("declaration", userService.getDeclarationByPatientId(currentUser.getId()));
		double doctorRatingDouble = userService.getRatingByDoctorId(doctorId);
		int doctorRating = (int) Math.floor(doctorRatingDouble);
		modelMap.put("doctorRating", doctorRating);
		modelMap.put("certification", userService.getCertificationByUserId(doctorId));
		List<Review> reviewList = userService.getAllReviewByDoctorId(doctorId);
		modelMap.put("reviewList", reviewList);
		return "doctors/doctorDetail";
	}

	@GetMapping("/recordMedical/{id}")
	public String recordMedical(@PathVariable("id") int patientId, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		Patient patient = (Patient) userService.findById(patientId);
		List<History> listHistory = userService.getHistoryByPatientId(patientId);
		model.addAttribute("patient", patient);
		model.addAttribute("listHistory", listHistory);
		return "patients/recordMedical";
	}

	@GetMapping("/file/{fileId}")
	public ResponseEntity<Resource> accessFile(@PathVariable Integer fileId) {
		// Load file from database
		FileModel file = userService.getFileByFileId(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
				.header("attachment; filename=\"" + file.getName() + "\"").body(new ByteArrayResource(file.getData()));
	}

	@PostMapping("/file/saveCertification")
	public @ResponseBody ResponseEntity<?> saveCertification(@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal CustomUserDetails currentUser) throws IOException {
		if (file.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.saveCertificationByDoctor(file, currentUser.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/file/saveImageProfile")
	public @ResponseBody ResponseEntity<?> saveImageProfileUser(@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal CustomUserDetails currentUser) throws IOException {
		if (file.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.saveImageProfileByUser(file, currentUser.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		return "access-denied";
	}

}
