package zagvladimir.service;


import zagvladimir.dto.MailParams;

import javax.mail.MessagingException;

public interface MailSenderService {

    void sendMessageUsingThymeleafTemplate(MailParams mailParams) throws MessagingException;

    void sendConfirmBookingMail(MailParams mailParams) throws MessagingException;
}
