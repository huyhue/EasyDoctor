package fpt.edu.vn.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import fpt.edu.vn.model.User;
import fpt.edu.vn.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final String baseUrl;

    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, @Value("${base.url}") String baseUrl) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.baseUrl = baseUrl;
    }

    @Async
    @Override
    public void sendEmail(String to, String subject, String templateName, Context templateContext, File attachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

//            String html = templateEngine.process("", templateContext);

            helper.setTo(to);
            helper.setFrom("spring.email.auth@gmail.com");
            helper.setSubject(subject);
            helper.setText(templateName, true);

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
    public void sendConfirmRegistration(User user) {
        Context context = new Context();
//        context.setVariable("user", user);
//        context.setVariable("url", baseUrl + "/register/confirm?token=" + user.getConfirmationToken());
        
        String content = "<h1>To confirm your e-mail address, please click the link below:</h1> \n"
				+ baseUrl + "/register/confirm?token=" + user.getConfirmationToken();
        
        sendEmail(user.getEmail(), "Confirm Registration", content, context, null);
    }
    
    
}
