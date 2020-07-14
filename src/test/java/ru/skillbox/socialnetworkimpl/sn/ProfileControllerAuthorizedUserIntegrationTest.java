package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("manovich@mail.ru")
public class ProfileControllerAuthorizedUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCurrentUserTest() throws Exception {
        mockMvc.perform(
                get("/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    public void editCurrentUserTest() throws Exception {
        String requestBody = "{\n" +
                "  \"first_name\": \"Qwe\",\n" +
                "  \"last_name\": \"Asd\",\n" +
                "  \"birth_date\": 1559751301818,\n" +
                "  \"phone\": \"89100000000\",\n" +
                "  \"photo\": \"photo\",\n" +
                "  \"about\": \"Родился в небольшой, но честной семье\",\n" +
                "  \"town_id\": 1,\n" +
                "  \"country_id\": 1,\n" +
                "  \"messages_permission\": \"ALL\"\n" +
                "}";
        mockMvc.perform(
                put("/users/me")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    public void deleteCurrentUserTest() throws Exception {
        mockMvc.perform(
                delete("/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").value("Ok"));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(
                get("/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    public void getPersonsWallPostsTest() throws Exception {
        mockMvc.perform(
                get("/users/{id}/wall", 1)
                        .param("offset", "0")
                        .param("itemPerPage", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data[0]").exists());
    }

    @Test
    public void addPostToUsersWallTest() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"asd\",\n" +
                "  \"post_text\": \"asd\"\n" +
                "}";
        mockMvc.perform(
                post("/users/{id}/wall", 2)
                        .param("publish_date", "1559751301818")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.author_id.id").value("2"));
    }

    @Test
    public void searchPersonTest() throws Exception {
        mockMvc.perform(
                get("/users/search/")
                        .param("first_name", "Paul")
                        .param("last_name", "Estiner")
                        .param("age_from", "0")
                        .param("age_to", "30")
                        .param("country_id", "1")
                        .param("city_id", "1")
                        .param("offset", "0")
                        .param("itemPerPage", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].first_name").value("Paul"))
                .andExpect(jsonPath("$.data[0].last_name").value("Estiner"));
    }

    @Test
    public void blockUserByIdTest() throws Exception {
        mockMvc.perform(
                put("/users/block/{id}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    public void unblockUserByIdTest() throws Exception {
        mockMvc.perform(
                delete("/users/block/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
