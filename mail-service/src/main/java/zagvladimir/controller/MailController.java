package zagvladimir.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zagvladimir.dto.MailParams;
import zagvladimir.service.impl.MailSenderServiceImpl;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailSenderServiceImpl mailSenderServiceImpl;

    public MailController(MailSenderServiceImpl mailSenderServiceImpl) {
        this.mailSenderServiceImpl = mailSenderServiceImpl;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendActivationMail(@RequestBody MailParams mailParams) throws MessagingException {
        mailSenderServiceImpl.sendMessageUsingThymeleafTemplate(mailParams);
        return ResponseEntity.ok().build();
    }
}
