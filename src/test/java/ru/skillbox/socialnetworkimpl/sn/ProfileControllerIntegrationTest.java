package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("manovich@mail.ru")
    public void getCurrentUserTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    @WithUserDetails("manovich@mail.ru")
    public void editCurrentUserTest() throws Exception {
        mockMvc.perform(
                put("/api/v1/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    @WithUserDetails("manovich@mail.ru")
    public void deleteCurrentUserTest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    @WithUserDetails("manovich@mail.ru")
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @WithUserDetails("manovich@mail.ru")
    public void getPersonsWallPostsTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/{id}/wall", 1)
                        .param("offset", "0")
                        .param("itemPerPage", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data[0]").exists());
    }

    @Test
    @WithUserDetails("manovich@mail.ru")
    public void addPostToUsersWallTest() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"string\",\n" +
                "  \"post_text\": \"string\"\n" +
                "}";
        mockMvc.perform(
                post("/api/v1/users/{id}/wall", 2)
                        .param("publishDate", "10-10-2020")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.error").value("Ok"))
                .andExpect(jsonPath("$.data[0]").exists())
                .andExpect(jsonPath("$.data.title").value("string"))
                .andExpect(jsonPath("$.data.post_text").value("string"))
                .andExpect(jsonPath("$data[0].author.id").value(2));
    }

    @Test
    @WithUserDetails("manovich@mail.ru")
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].first_name").value("Paul"))
                .andExpect(jsonPath("$.data[0].last_name").value("Estiner"));
    }
}
