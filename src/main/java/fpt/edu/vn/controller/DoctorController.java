package fpt.edu.vn.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fpt.edu.vn.component.ChangePasswordForm;
import fpt.edu.vn.component.TimePeroid;
import fpt.edu.vn.component.UserForm;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Patient;
import fpt.edu.vn.model.User;
import fpt.edu.vn.model.WorkingPlan;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.PackagesService;
import fpt.edu.vn.service.UserService;
import fpt.edu.vn.service.WorkingPlanService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

	private final UserService userService;
	private final EmailService emailService;
	private final PackagesService packagesService;
	private final WorkingPlanService workingPlanService;

	public DoctorController(UserService userService, EmailService emailService, PackagesService packagesService,
			WorkingPlanService workingPlanService) {
		super();
		this.userService = userService;
		this.emailService = emailService;
		this.packagesService = packagesService;
		this.workingPlanService = workingPlanService;
	}

	@GetMapping("/home")
	public String showHomeDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", userService.getUserById(currentUser.getId()));
		return "doctors/home";
	}
	
	@GetMapping("/all")
	public String showAllDoctors(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("doctors", userService.getAllDoctorsByPatient());
		return "doctors/doctorList";
	}
	
	@GetMapping("/{id}")
    public String showDoctorDetails(@PathVariable("id") int doctorId, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		Doctor doctor = userService.getDoctorById(doctorId);
		if (currentUser.getId() == doctorId || currentUser.hasRole("ROLE_ADMIN")) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", doctor);
            }
            if (!model.containsAttribute("passwordChange")) {
                model.addAttribute("passwordChange", new ChangePasswordForm(doctorId));
            }
            model.addAttribute("account_type", "doctors");
            model.addAttribute("allPackages", packagesService.getAllPackages());
            model.addAttribute("formActionProfile", "/doctors/update/profile");
            model.addAttribute("formActionPassword", "/doctors/update/password");
            model.addAttribute("numberOfScheduledAppointments", 1);
            model.addAttribute("numberOfCanceledAppointments", 2);
            return "users/updateUserForm";
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }

    @PostMapping("/update/profile")
    public String processDoctorUpdate(@ModelAttribute("user") Doctor user, BindingResult bindingResult) {
//    	if (bindingResult.hasErrors()) {
//			return "redirect:/doctors/" + user.getId();
//		}
    	userService.updateDoctor(user);
        return "redirect:/doctors/" + user.getId();
    }
    
    @PostMapping("/update/password")
    public String processProviderPasswordUpate(@ModelAttribute("passwordChange") ChangePasswordForm passwordChange, BindingResult bindingResult) {
        userService.updateUserPassword(passwordChange);
        return "redirect:/doctors/" + passwordChange.getId();
    }
    
    @GetMapping("/availability")
    public String showAvailability(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        model.addAttribute("plan", workingPlanService.getWorkingPlanByDoctorId(currentUser.getId()));
        model.addAttribute("breakModel", new TimePeroid());
        return "doctors/doctorAvailability";
    }
    
    @PostMapping("/availability")
    public String processWorkingPlanUpdate(@ModelAttribute("plan") WorkingPlan plan) {
        workingPlanService.updateWorkingPlan(plan);
        return "redirect:/doctors/availability";
    }
    
    //http://localhost:8080/providers/availability/breakes/add
    @PostMapping("/availability/breakes/add")
    public String processAddBreak(@ModelAttribute("breakModel") TimePeroid breakToAdd, @RequestParam("planId") int planId, @RequestParam("dayOfWeek") String dayOfWeek) {
        System.out.print("Test: "+breakToAdd+planId+dayOfWeek);
    	workingPlanService.addBreakToWorkingPlan(breakToAdd, planId, dayOfWeek);
        return "redirect:/doctors/availability";
    }

    @PostMapping("/availability/breakes/delete")
    public String processDeleteBreak(@ModelAttribute("breakModel") TimePeroid breakToDelete, @RequestParam("planId") int planId, @RequestParam("dayOfWeek") String dayOfWeek) {
        workingPlanService.deleteBreakFromWorkingPlan(breakToDelete, planId, dayOfWeek);
        return "redirect:/doctors/availability";
    }

    @GetMapping("/new")
    public String showDoctorRegistrationForm(Model model) {

        return "users/";
    }

    @PostMapping("/new")
    public String processDoctorRegistrationForm() {

        return "redirect:/Doctors/all";
    }

    @PostMapping("/delete")
    public String processDeleteDoctorRequest(@RequestParam("DoctorId") int DoctorId) {

        return "redirect:/Doctors/all";
    }

	
}
