package fpt.edu.vn.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fpt.edu.vn.model.Notification;
import fpt.edu.vn.security.CustomUserDetails;
import fpt.edu.vn.service.NotificationService;
import fpt.edu.vn.service.UserService;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;
	private final UserService userService;

	public NotificationController(NotificationService notificationService, UserService userService) {
		this.notificationService = notificationService;
		this.userService = userService;
	}

	@GetMapping()
	public String showUserNotificationList(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Notification> list = userService.getUserById(currentUser.getId()).getNotifications();
//    	Collections.sort(list, Collections.reverseOrder());
//		Comparator<Notification> reverseComparator = (c1, c2) -> {
//			return c2.getCreatedAt().compareTo(c1.getCreatedAt());
//		};
//		Collections.sort(list, reverseComparator);
		model.addAttribute("notifications", list);
		return "notifications/listNotifications";
	}

	@GetMapping("/{notificationId}")
	public String showNotification(@PathVariable("notificationId") int notificationId,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		Notification notification = notificationService.getNotificationById(notificationId);
		notificationService.markAsRead(notificationId, currentUser.getId());
		return "redirect:" + notification.getUrl();
	}

	@PostMapping("/markAllAsRead")
	public String processMarkAllAsRead(@AuthenticationPrincipal CustomUserDetails currentUser) {
		notificationService.markAllAsRead(currentUser.getId());
		return "redirect:/notifications";
	}

}
