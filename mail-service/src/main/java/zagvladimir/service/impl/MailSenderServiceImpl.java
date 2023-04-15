package zagvladimir.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import zagvladimir.dto.MailParams;
import zagvladimir.service.MailSenderService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:mail.properties")
public class MailSenderServiceImpl implements MailSenderService {

    @Value("${spring.mail.username}")
    private String noReplyAddress;

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    public static final String EMAIL_ACTIVATE_TEMPLATE = "email-template.html";
    public static final String EMAIL_CONFIRM_TEMPLATE = "confirm-email-template.html";

    public void sendMessageUsingThymeleafTemplate(MailParams mailParams) throws MessagingException {
        log.info("Method sendMessageUsingThymeleafTemplate in MailSenderServiceImpl start");
        sendMail(mailParams, EMAIL_ACTIVATE_TEMPLATE);
        log.info("Method sendMessageUsingThymeleafTemplate in MailSenderServiceImpl finish");
    }

    public void sendConfirmBookingMail(MailParams mailParams) throws MessagingException {
        sendMail(mailParams, EMAIL_CONFIRM_TEMPLATE);
    }

    private void sendMail(MailParams mailParams, String template) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(mailParams.getTemplateModel());
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(noReplyAddress);
        helper.setTo(mailParams.getEmailTo());
        helper.setSubject(mailParams.getSubject());
        helper.setText(htmlBody, true);

        if (mailParams.getPathToBilling() != null) {
            File file = new File(mailParams.getPathToBilling());
            helper.addAttachment("billing.pdf", file);
        }
        emailSender.send(message);
        log.info("Email sent to {} successfully", mailParams.getEmailTo());
    }
}
