package zagvladimir.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import zagvladimir.dto.MailParams;
import zagvladimir.service.impl.MailSenderServiceImpl;

import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final MailSenderServiceImpl mailSenderServiceImpl;

    @KafkaListener(topics = "mail-topic", groupId = "myGroup")
    public void listenGroupFoo(MailParams message) throws MessagingException {
        mailSenderServiceImpl.sendMessageUsingThymeleafTemplate(message);
    }
}
