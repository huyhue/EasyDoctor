package fpt.edu.vn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fpt.edu.vn.component.ChatMessage;
import fpt.edu.vn.component.ReviewForm;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.History;
import fpt.edu.vn.model.Message;
import fpt.edu.vn.model.Review;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.OTPService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
	
	private static final String REJECTION_CONFIRMATION_VIEW = "appointments/rejectionConfirmation";
	
	@Autowired
	public OTPService otpService;
	private final PackagesService packagesService;
	private final UserService userService;
	private final AppointmentService appointmentService;
	private final EmailService emailService;
	
	public AppointmentController(PackagesService packagesService, UserService userService,
			AppointmentService appointmentService, EmailService emailService) {
		super();
		this.packagesService = packagesService;
		this.userService = userService;
		this.appointmentService = appointmentService;
		this.emailService = emailService;
	}

	@GetMapping("/{id}")
    public String showAppointmentDetail(@PathVariable("id") int appointmentId, Model model, ModelMap modelMap, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Appointment appointment = appointmentService.getAppointmentByIdWithAuthorization(appointmentId);
        model.addAttribute("appointment", appointment);
        model.addAttribute("chatMessage", new Message());
		model.addAttribute("history", new History());
        boolean allowedToRequestRejection = appointmentService.isPatientAllowedToRejectAppointment(currentUser.getId(), appointmentId);
        boolean allowedToAcceptRejection = appointmentService.isDoctorAllowedToAcceptRejection(currentUser.getId(), appointmentId);
        boolean allowedToReview = appointmentService.isPatientAllowedToReview(currentUser.getId(), appointmentId);
        model.addAttribute("allowedToRequestRejection", allowedToRequestRejection);
        model.addAttribute("allowedToAcceptRejection", allowedToAcceptRejection);
        setUpReferenceData(modelMap);
        model.addAttribute("allowedToReview", allowedToReview);
        model.addAttribute("reviewForm", new ReviewForm());
        if (allowedToRequestRejection) {
            model.addAttribute("remainingTime", formatDuration(Duration.between(LocalDateTime.now(), appointment.getEnd().plusDays(1))));
        }
        
        String cancelNotAllowedReason = appointmentService.getCancelNotAllowedReason(currentUser.getId(), appointmentId);
        model.addAttribute("allowedToCancel", cancelNotAllowedReason == null);
        model.addAttribute("cancelNotAllowedReason", cancelNotAllowedReason);
        return "appointments/appointmentDetail";
    }
	
    @PostMapping("/review")
    public String addReviewPatient(@ModelAttribute("reviewForm") ReviewForm reviewForm, @AuthenticationPrincipal CustomUserDetails currentUser) {
    	Review reviewUO = new Review(reviewForm.getFeedback(), reviewForm.getRating(),reviewForm.getDoctor(), 
    			userService.getPatientById(currentUser.getId()));
    	
    	appointmentService.saveReviewByAppointment(reviewUO, reviewForm.getAppointmentId());
        return "redirect:/detail/" + reviewForm.getDoctor().getId();
    }
	
	private void setUpReferenceData(ModelMap modelMap) {
		Map<Integer, String> ratingOptionMap = new LinkedHashMap<>();
		ratingOptionMap.put(5, "5 Star");
		ratingOptionMap.put(4, "4 Star");
		ratingOptionMap.put(3, "3 Star");
		ratingOptionMap.put(2, "2 Star");
		ratingOptionMap.put(1, "1 Star");
		modelMap.put("ratingOptionMap", ratingOptionMap);
	}

	@GetMapping("/all")
	public String showAllAppointments(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        if (currentUser.hasRole("ROLE_PATIENT")) {
            model.addAttribute("appointments", appointmentService.getAppointmentByPatientId(currentUser.getId()));
        } else if (currentUser.hasRole("ROLE_DOCTOR")) {
            model.addAttribute("appointments", appointmentService.getAppointmentByDoctorId(currentUser.getId()));
        }
		return "appointments/listAppointments";
	}

	@GetMapping("/new/{doctorId}")
	public String selectPackages(@PathVariable("doctorId") int doctorId, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("packages", packagesService.getPackagesByDoctorId(doctorId));
		model.addAttribute(doctorId);
		return "appointments/selectPackages";
	}

	@GetMapping("/new/{doctorId}/{packagesId}")
	public String selectDate(@PathVariable("packagesId") int packagesId, @PathVariable("doctorId") int doctorId,
			Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute(doctorId);
		model.addAttribute("packagesId", packagesId);
		return "appointments/selectDate";
	}

	@GetMapping("/new/{doctorId}/{packagesId}/{dateTime}")
	public String showNewAppointmentSummary(@PathVariable("packagesId") int packagesId,
			@PathVariable("doctorId") int doctorId, @PathVariable("dateTime") String start, Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		if (appointmentService.isAvailable(packagesId, doctorId, currentUser.getId(), LocalDateTime.parse(start))) {
			model.addAttribute("packages", packagesService.getPackagesById(packagesId));
			model.addAttribute("doctor", userService.getDoctorById(doctorId).getFullname());
			model.addAttribute(doctorId);
			model.addAttribute("start", LocalDateTime.parse(start));
			model.addAttribute("end",
					LocalDateTime.parse(start).plusMinutes(packagesService.getPackagesById(packagesId).getDuration()));
			emailService.sendAppointmentOTPConfirm(currentUser.getEmail());
			return "appointments/newAppointmentSummary";
		} else {
			return "redirect:/appointments/new" + doctorId;
		}
	}

	@PostMapping("/new")
	public String bookAppointment(@RequestParam("packagesId") int packagesId, @RequestParam("doctorId") int doctorId,
			@RequestParam("start") String start, @AuthenticationPrincipal CustomUserDetails currentUser) {
		appointmentService.createNewAppointment(packagesId, doctorId, currentUser.getId(), LocalDateTime.parse(start));
		return "redirect:/appointments/all";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public @ResponseBody String validateOtp(@RequestParam("OTPSEND") int OTPSEND, @RequestParam("packagesId") int packagesId, @RequestParam("doctorId") int doctorId,
			@RequestParam("start") String start, @AuthenticationPrincipal CustomUserDetails currentUser) {
		final String SUCCESS = "SUCCESS";
		final String FAIL = "FAIL";
		if (OTPSEND >= 0) {
			int serverOtp = otpService.getOtp(currentUser.getEmail());
			if (serverOtp > 0) {
				if (OTPSEND == serverOtp) {
					otpService.clearOTP(currentUser.getEmail());
					appointmentService.createNewAppointment(packagesId, doctorId, currentUser.getId(), LocalDateTime.parse(start));
					return (SUCCESS);
				} else {
					return FAIL;
				}
			} else {
				return FAIL;
			}
		} else {
			return FAIL;
		}
	}

	@PostMapping("/cancel")
	public String cancelAppointment(@RequestParam("appointmentId") int appointmentId,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		appointmentService.cancelUserAppointmentById(appointmentId, currentUser.getId());
		return "redirect:/appointments/all";
	}

    @PostMapping("/reject")
    public String processAppointmentRejectionRequest(@RequestParam("appointmentId") int appointmentId, @AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
        boolean result = appointmentService.requestAppointmentRejection(appointmentId, currentUser.getId());
        model.addAttribute("result", result);
        model.addAttribute("type", "request");
        return REJECTION_CONFIRMATION_VIEW;
    }
    
    //Click link by email by doctor
    @GetMapping("/acceptRejection")
    public String acceptAppointmentRejectionRequest(@RequestParam("token") String token, Model model) {
        boolean result = appointmentService.acceptRejection(token);
        model.addAttribute("result", result);
        model.addAttribute("type", "accept");
        return REJECTION_CONFIRMATION_VIEW;
    }
    
    //Click link by email
    @GetMapping("/reject")
    public String processAppointmentRejectionRequest(@RequestParam("token") String token, Model model) {
        boolean result = appointmentService.requestAppointmentRejection(token);
        model.addAttribute("result", result);
        model.addAttribute("type", "request");
        return REJECTION_CONFIRMATION_VIEW;
    }

    //Click by button by doctor
    @PostMapping("/acceptRejection")
    public String acceptAppointmentRejectionRequest(@RequestParam("appointmentId") int appointmentId, @AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
        boolean result = appointmentService.acceptRejection(appointmentId, currentUser.getId());
        model.addAttribute("result", result);
        model.addAttribute("type", "accept");
        return REJECTION_CONFIRMATION_VIEW;
    }
    
    @RequestMapping(value = "/messages/all", method = RequestMethod.POST)
    @ResponseBody
    public String getMessages(@RequestParam("appointmentId") int appointmentId) {
		List<ChatMessage> list = appointmentService.getMessagesByAppointmentId(appointmentId);
		
		Gson gsonBuilder = new GsonBuilder().create();
        String messagelistJson = gsonBuilder.toJson(list);
    	return messagelistJson;
    }
    
    @RequestMapping(value = "/messages/active", method = RequestMethod.POST)
    @ResponseBody
    public Boolean getActiveUsers(@RequestParam("appointmentId") int appointmentId, @RequestParam("userId") int userId) {
    	boolean active = appointmentService.getActiveUserByAppointment(appointmentId, userId);
    	return active;
    }
    
    //Chưa xài
    @RequestMapping(value = "/messages/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchMessage(@RequestParam("content") String content) {
    	List<ChatMessage> list = appointmentService.searchContentInMessages(content);
		
		Gson gsonBuilder = new GsonBuilder().create();
        String messagelistJson = gsonBuilder.toJson(list);
    	return messagelistJson;
    }

	public static String formatDuration(Duration duration) {
		long s = duration.getSeconds();
		return String.format("%dh%02dm", s / 3600, (s % 3600) / 60);
	}

}
