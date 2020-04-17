package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(String sentTo, String subject, String text) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(sentTo);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
