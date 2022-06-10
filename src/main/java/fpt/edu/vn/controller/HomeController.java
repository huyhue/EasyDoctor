package fpt.edu.vn.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.util.StringUtils;
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
			model.addAttribute("alreadyRegisteredMessage", "Oops!  There is already a user registered.");
			return "users/registerPatient";
		} else {
			user.setConfirmationToken(UUID.randomUUID().toString());
			userService.savePatientRegister(user);
			emailService.sendRegisterSuccess(user);
			model.addAttribute("successMessage", "You registered successful.");
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
			model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
		} else {
			model.addAttribute("alreadyRegisteredMessage", "A confirmation e-mail not exists.");
		}
		return "users/forgotPassword";
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
		if (userDR != null) {
			userService.savePasswordByUser(user);
			model.addAttribute("successMessage", "Your password has been set!");
		} else {
			model.addAttribute("errorMessage", "Your password hasn't been set. Please again!");
		}
		return "users/login";
	}

	@PostMapping("/image/saveImageProfile")
	public @ResponseBody ResponseEntity<?> saveImageProfile(@AuthenticationPrincipal CustomUserDetails currentUser,
			final @RequestParam("profileImage") MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (file.getContentType().equalsIgnoreCase("image/jpg") || file.getContentType().equalsIgnoreCase("image/jpeg")
				|| file.getContentType().equalsIgnoreCase("image/png")) {
			double fileSize = file.getSize();

			double kl = (fileSize / 1024);
			double mb = (kl / 1024);
			if (mb < 5) {
				String uploadDir = "./uploads/" + currentUser.getEmail();
				Path uploadPath = Paths.get(uploadDir);
				String oldFileLocation = uploadDir + "/" + currentUser.getProfileImage();
				Path getImage = Paths.get(oldFileLocation);

				if (Files.exists(getImage)) {
					Files.delete(getImage);
					System.out.println("Files deleted");
				}

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try (InputStream inputStream = file.getInputStream()) {
					String newFileName = System.currentTimeMillis() + "_" + fileName;
					userService.updateImage(currentUser.getId(), newFileName);
					currentUser.setProfileImage(newFileName);
					Path filePath = uploadPath.resolve(newFileName).normalize();
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Error in uploading File!");
				}
			} else {
				return new ResponseEntity<>("file is too large", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("Please enter a valid image file", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/detail/{id}")
	public String showDoctorDetails(@PathVariable("id") int doctorId, ModelMap modelMap,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		Doctor doctor = userService.getDoctorById(doctorId);
		modelMap.put("doctor", doctor);
		
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

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header("attachment; filename=\"" + file.getName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }
	
	@PostMapping("/file/saveCertification")
	public @ResponseBody ResponseEntity<?> saveCertification(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal CustomUserDetails currentUser) throws IOException {
		if (file.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.saveCertificationByDoctor(file, currentUser.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/file/saveImageProfile")
	public @ResponseBody ResponseEntity<?> saveImageProfileUser(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal CustomUserDetails currentUser) throws IOException {
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
