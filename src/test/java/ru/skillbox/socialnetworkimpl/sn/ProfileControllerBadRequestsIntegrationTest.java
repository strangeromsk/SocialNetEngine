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
                get("/api/v1/users/{id}/wall", -1)
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
                "  \"123\": \"asd\",\n" +
                "  \"post_text\": \"asd\"\n" +
                "}";
        mockMvc.perform(
                post("/api/v1/users/{id}/wall", -5)
                        .param("publishDate", "1559751301818")
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
                get("/api/v1/users/search/")
                        .param("first_name", "q")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void blockUserByIdTest() throws Exception {
        mockMvc.perform(
                put("/api/v1/users/block/{id}", -1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }

    @Test
    public void unblockUserByIdTest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/users/block/{id}", -1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("invalid_request"));
    }
}
