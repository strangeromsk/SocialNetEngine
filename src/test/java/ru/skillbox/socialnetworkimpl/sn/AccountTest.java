package ru.skillbox.socialnetworkimpl.sn;

import org.hamcrest.Matcher;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void signUpAccountTest() throws Exception {

        String newUser = "{\"email\": \"4ew@yandex.ru\", \"passwd1\" : \"123\", \"passwd2\" : " +
                "\"123\", \"firstName\" : \"Vasia\", \"lastName\" : \"Pupkin\",\"code\" : \"123\"}";

        String answer = "{\"error\" : \"string\", \"timestamp\",\"123\",\"data\" : \" { \"message\":\"ok\"}\"}";
       // {"message":"ok"}


        mvc.perform(MockMvcRequestBuilders.post("/api/v1/account/register")
                    .content(newUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data"));

    }
}
