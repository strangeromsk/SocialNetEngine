package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("manovich@mail.ru")
public class ProfileControllerBadRequestsIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPersonsWallPostsTest() throws Exception {
        mockMvc.perform(
                get("/users/{id}/wall", -1)
                        .param("offset", "a")
                        .param("itemPerPage", "b")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void addPostToUsersWallTest() throws Exception {
        String requestBody = "{\n" +
                "  \"title\": \"asd\",\n" +
                "  \"post_text\": \"asd\"\n" +
                "}";
        mockMvc.perform(
                post("/users/{id}/wall", 2)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void searchPersonTest() throws Exception {
        mockMvc.perform(
                get("/users/search/")
                        .param("first_name", "q")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void blockUserByIdTest() throws Exception {
        mockMvc.perform(
                put("/users/block/{id}", -1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void unblockUserByIdTest() throws Exception {
        mockMvc.perform(
                delete("/users/block/{id}", -1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }
}
