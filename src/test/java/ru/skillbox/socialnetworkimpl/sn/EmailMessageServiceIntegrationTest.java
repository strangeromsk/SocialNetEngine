package ru.skillbox.socialnetworkimpl.sn;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetworkimpl.sn.services.EmailMessageService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailMessageServiceIntegrationTest {

    @Autowired
    private EmailMessageService emailMessageService;

    @Rule
    private SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Test
    public void sendMessageTest() throws MessagingException {
        emailMessageService.sendMessage("info@mail.ru", "subject", "text");
        MimeMessage[] messages = smtpServerRule.getMessages();
        assertEquals(1, messages.length);
        MimeMessage current = messages[0];
        assertEquals("subject", current.getSubject());
    }
}
