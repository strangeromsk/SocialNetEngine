package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.skillbox.socialnetworkimpl.sn.services.EmailMessageService;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class EmailMessageServiceIntegrationTest {

    @InjectMocks
    private EmailMessageService emailMessageService;

    @Mock
    private JavaMailSender emailSender;

    @Test
    public void sendMessageTest() {
        String sentTo = "info@gmail.com";
        String subject = "subject";
        String text = "text";
        emailMessageService.sendMessage(sentTo, subject, text);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sentTo);
        message.setSubject(subject);
        message.setText(text);
        verify(emailSender).send(message);
    }
}
