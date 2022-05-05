package fpt.edu.vn.service.impl;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import fpt.edu.vn.service.AppointmentService;
import fpt.edu.vn.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final int NUMBER_OF_ALLOWED_CANCELATIONS_PER_MONTH = 1;
//    private final AppointmentRepository appointmentRepository;
//    private final UserService userService;
//    private final WorkService workService;
//    private final ChatMessageRepository chatMessageRepository;
//    private final NotificationService notificationService;
//    private final JwtTokenServiceImpl jwtTokenService;
//
//    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService, WorkService workService, ChatMessageRepository chatMessageRepository, NotificationService notificationService, JwtTokenServiceImpl jwtTokenService) {
//        this.appointmentRepository = appointmentRepository;
//        this.userService = userService;
//        this.workService = workService;
//        this.chatMessageRepository = chatMessageRepository;
//        this.notificationService = notificationService;
//        this.jwtTokenService = jwtTokenService;
//    }

    
}
