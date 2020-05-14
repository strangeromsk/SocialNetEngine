package ru.skillbox.socialnetworkimpl.sn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void signUpAccountTestRegisteredTest() throws Exception {

        String correctNewUser = "{\"email\": \"newuser@u5ser.my\", \"passwd1\" : \"123\", \"passwd2\" : " +
                "\"123\", \"firstName\" : \"Vasia\", \"lastName\" : \"Pupkin\",\"code\" : \"123\"}";
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "ok");
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/account/register")
                    .content(correctNewUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data", is(data)));
    }

    @Test
    public void signUpAccountBadEmailTest() throws Exception {
        String incEmailNewUser = "{\"email\": \"4ewyandex.ru\", \"passwd1\" : \"123\", \"passwd2\" : " +
                "\"123\", \"firstName\" : \"Vasia\", \"lastName\" : \"Pupkin\",\"code\" : \"123\"}";

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/account/register")
                .content(incEmailNewUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void signUpAccountBadPasswordTest() throws Exception {
        String incPassNewUser = "{\"email\": \"4ew@yandex.ru\", \"passwd1\" : \"123\", \"passwd2\" : " +
                "\"1235\", \"firstName\" : \"Vasia\", \"lastName\" : \"Pupkin\",\"code\" : \"123\"}";

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/account/register")
                .content(incPassNewUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void recoverPasswordOkTest() throws Exception {
        String email = "{\"email\": \"user@user.exist\"}";
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "ok");
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/account/password/recovery")
                .content(email).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data", is(data)));

    }

    @Test
    public void recoverPassswordNotExistUserTest() throws Exception {
        String email = "{\"email\": \"not@exist.user\"}";
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/account/password/recovery")
                .content(email).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void setPasswordOkTest() throws Exception {
        String email = "{\"token\": \"dfsfsd\", \"password\": \"newPassword\"}";
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "ok");
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/account/password/set")
                .content(email).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data", is(data)));
    }

    @Test
    public void changeEmailOkTest() throws Exception {
        String email = "{\"email\": \"new@email.mail\"}";
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "ok");
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/account/email")
                .content(email).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data", is(data)));
    }

    @Test
    public void changeEmailBadEmailTest() throws Exception {
        String incEmailNewUser = "{\"email\": \"4ewyandex.ru\"}";

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/account/email")
                .content(incEmailNewUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
