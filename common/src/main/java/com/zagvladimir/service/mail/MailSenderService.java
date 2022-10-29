package com.zagvladimir.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@PropertySource("classpath:mail.properties")
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String noreplyAddress;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("email-template.html", thymeleafContext);
        send(to, subject, htmlBody);
    }

    public void sendConfirmBookingMail(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("confirm-email-template.html", thymeleafContext);
        send(to, subject, htmlBody);
    }

    private void send(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(noreplyAddress);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

}
