package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerUnauthorizedUserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCurrentUserTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
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
                put("/api/v1/users/me")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void deleteCurrentUserTest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void getPersonsWallPostsTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/{id}/wall", 1)
                        .param("offset", "0")
                        .param("itemPerPage", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void addPostToUsersWallTest() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"asd\",\n" +
                "  \"post_text\": \"asd\"\n" +
                "}";
        mockMvc.perform(
                post("/api/v1/users/{id}/wall", 2)
                        .param("publishDate", "1559751301818")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void searchPersonTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/search/")
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
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void blockUserByIdTest() throws Exception {
        mockMvc.perform(
                put("/api/v1/users/block/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void unblockUserByIdTest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/users/block/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }
}
