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
    private String noreplyAddress;

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    public void sendMessageUsingThymeleafTemplate(MailParams mailParams) throws MessagingException {
        log.info("Method sendMessageUsingThymeleafTemplate in MailSenderServiceImpl start");
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(mailParams.getTemplateModel());
        String htmlBody = thymeleafTemplateEngine.process("email-template.html", thymeleafContext);
        send(mailParams, htmlBody);
        log.info("Method sendMessageUsingThymeleafTemplate in MailSenderServiceImpl finish");
    }

    public void sendConfirmBookingMail(MailParams mailParams) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(mailParams.getTemplateModel());
        String htmlBody =
                thymeleafTemplateEngine.process("confirm-email-template.html", thymeleafContext);
        send(mailParams, htmlBody);
    }

    private void send(MailParams mailParams, String htmlBody) throws MessagingException {
        log.info("Method send in MailSenderServiceImpl start");
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(noreplyAddress);
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
