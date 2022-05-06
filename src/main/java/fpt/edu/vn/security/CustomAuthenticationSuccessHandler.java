package fpt.edu.vn.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    private final AppointmentService appointmentService;
//
//    public CustomAuthenticationSuccessHandler(AppointmentService appointmentService) {
//        this.appointmentService = appointmentService;
//    }
	public CustomAuthenticationSuccessHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

//        if (currentUser.hasRole("ROLE_ADMIN")) {
//            appointmentService.updateAllAppointmentsStatuses();
//        } else {
//            appointmentService.updateUserAppointmentsStatuses(currentUser.getId());
//        }
		if (currentUser.hasRole("ROLE_PATIENT")) {
			response.sendRedirect(request.getContextPath() + "/doctors/all");
		} else if (currentUser.hasRole("ROLE_DOCTOR")) {
			response.sendRedirect(request.getContextPath() + "/doctors/home");
		} else if (currentUser.hasRole("ROLE_ADMIN")){
			response.sendRedirect(request.getContextPath() + "/admin/home");
		}

	}

}
