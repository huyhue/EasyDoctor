package fpt.edu.vn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import fpt.edu.vn.component.DoctorDto;
import fpt.edu.vn.model.Appointment;
import fpt.edu.vn.model.Doctor;
import fpt.edu.vn.model.Invoice;
import fpt.edu.vn.model.User;
import fpt.edu.vn.service.EmailService;
import fpt.edu.vn.service.OTPService;
import fpt.edu.vn.util.PdfGeneratorUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	public OTPService otpService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final String baseUrl;
    private final JwtTokenServiceImpl jwtTokenService;
    private final PdfGeneratorUtil pdfGenaratorUtil;
    
    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, @Value("${base.url}") String baseUrl,
			JwtTokenServiceImpl jwtTokenService, PdfGeneratorUtil pdfGenaratorUtil) {
		super();
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
		this.baseUrl = baseUrl;
		this.jwtTokenService = jwtTokenService;
		this.pdfGenaratorUtil = pdfGenaratorUtil;
	}

	@Async
    @Override
    public void sendEmail(String to, String subject, String templateName, Context templateContext, File attachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            String html = templateEngine.process("email/" + templateName, templateContext);

            helper.setTo(to);
            helper.setFrom("spring.email.auth@gmail.com");
            helper.setSubject(subject);
//            helper.setText(templateName, true);
            helper.setText(html, true);

            if (attachment != null) {
                helper.addAttachment("invoice", attachment);
            }
            javaMailSender.send(message);

        } catch (MessagingException e) {
            System.out.print("Error while adding attachment to email, error is {}"+ e.getLocalizedMessage());
        }
    }

    @Async
    @Override
    public void sendRegisterSuccess(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        sendEmail(user.getEmail(), "????ng k?? th??nh c??ng", "registerSucess", context, null);
    }
    
    @Async
    @Override
    public void sendConfirmForgotPassword(User user) {
    	Context context = new Context();
    	context.setVariable("user", user);
    	context.setVariable("url", baseUrl + "/register/confirm?token=" + user.getConfirmationToken());
    	sendEmail(user.getEmail(), "X??c nh???n qu??n m???t kh???u", "confirmForgotPassword", context, null);
    }
    
    @Async
    @Override
    public void sendAppointmentRejectionRequested(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("url", baseUrl + "/appointments/acceptRejection?token=" + jwtTokenService.generateAcceptRejectionToken(appointment));
        
        sendEmail(appointment.getDoctor().getEmail(), "Y??u c???u t??? ch???i l???ch kh??m", "appointmentRejectionRequested", context, null);
    }
    
    @Async
    @Override
    public void sendAppointmentRejectionAccepted(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        sendEmail(appointment.getPatient().getEmail(), "Y??u c???u t??? ch???i l???ch kh??m ???????c ch???p nh???n", "appointmentRejectionAccepted", context, null);
    }
    
    @Async
    @Override
    public void sendNewAppointment(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        sendEmail(appointment.getDoctor().getEmail(), "?????t l???ch kh??m m???i", "appointmentNew", context, null);
    }
    
    @Async
    @Override
    public void sendAppointmentCanceledByPatient(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("canceler", "patient");
        sendEmail(appointment.getDoctor().getEmail(), "L???ch kh??m b???nh ???? h???y b???i b???nh nh??n", "appointmentCanceled", context, null);
    }

    @Async
    @Override
    public void sendAppointmentCanceledByDoctor(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("canceler", "doctor");
        sendEmail(appointment.getPatient().getEmail(), "L???ch kh??m b???nh ???? h???y b???i b??c s??", "appointmentCanceled", context, null);
    }
    
    @Async
    @Override
    public void sendAppointmentFinished(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("url", baseUrl + "/appointments/reject?token=" + jwtTokenService.generateAppointmentRejectionToken(appointment));
        sendEmail(appointment.getPatient().getEmail(), "L???ch kh??m b???nh ???? k???t th??c", "appointmentFinished", context, null);
    }
    
    @Async
    @Override
    public void sendInvoice(Invoice invoice) {
        Context context = new Context();
        context.setVariable("patient", invoice.getAppointments().get(0).getPatient().getFullname());
        try {
            File invoicePdf = pdfGenaratorUtil.generatePdfFromInvoice(invoice);
            sendEmail(invoice.getAppointments().get(0).getPatient().getEmail(), "H??a ????n kh??m b???nh", "appointmentInvoice", context, invoicePdf);
        } catch (Exception e) {
            System.err.print("Error while generating pdf, error is {}"+ e.getLocalizedMessage());
        }
    }
    
    @Async
    @Override
    public void sendAppointmentOTPConfirm(String email) {
    	int otp = otpService.generateOTP(email);
        Context context = new Context();
        context.setVariable("OTPSEND", String.valueOf(otp));
        sendEmail(email, "X??c nh???n ?????t l???ch h???n", "appointmentConfirmOTP", context, null);
    }
    
    @Async
    @Override
    public void sendInfoNewDoctor(DoctorDto doctordto) {
    	Context context = new Context();
    	context.setVariable("doctor", doctordto);
    	sendEmail(doctordto.getEmail(), "????ng k?? b??c s?? th??nh c??ng", "infoNewDoctor", context, null);
    }
}
