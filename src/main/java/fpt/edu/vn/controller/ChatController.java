package fpt.edu.vn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import fpt.edu.vn.component.ChatMessage;
import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.UserService;

@Controller
public class ChatController {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final UserService userService;
    private final AppointmentService appointmentService;
    
    public ChatController(UserService userService, AppointmentService appointmentService) {
		super();
		this.userService = userService;
		this.appointmentService = appointmentService;
	}

	@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		logger.info("User ChatMessage : " + chatMessage.toString());
        if (chatMessage.getType() == ChatMessage.MessageType.CHAT) {
            appointmentService.addMessageToAppointmentChat(chatMessage);
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
    	logger.info("User controller : " + chatMessage.getSender()+chatMessage.getSender_id());
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("user_id", chatMessage.getSender_id());

        userService.updateUserActiveState(chatMessage.getSender_id(), true);

        return chatMessage;
    }

}
