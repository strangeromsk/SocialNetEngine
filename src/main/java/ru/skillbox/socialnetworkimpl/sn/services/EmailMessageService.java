package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageService {
//    временный костыль
    private JavaMailSender emailSender = new JavaMailSenderImpl();

    public void sendMessage(String sentTo, String subject, String text) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(sentTo);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);

    }
}
