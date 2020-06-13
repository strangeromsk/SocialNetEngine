package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailMessageServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sendMessageTest() {

    }
}
