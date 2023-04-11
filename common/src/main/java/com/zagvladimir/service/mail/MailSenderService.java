package com.zagvladimir.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:mail.properties")
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String noreplyAddress;

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    public void sendMessageUsingThymeleafTemplate(String to,
                                                  String subject,
                                                  Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("email-template.html", thymeleafContext);
        send(to, subject, htmlBody, null);
    }

    public void sendConfirmBookingMail(String to,
                                       String subject,
                                       String pathToBilling,
                                       Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody =
                thymeleafTemplateEngine.process("confirm-email-template.html", thymeleafContext);
        send(to, subject, htmlBody, pathToBilling);
    }

    private void send(String to,
                      String subject,
                      String htmlBody,
                      String pathToBilling) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(noreplyAddress);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (pathToBilling != null) {
            File file = new File(pathToBilling);
            helper.addAttachment("billing.pdf", file);
        }
        emailSender.send(message);
    }
}
