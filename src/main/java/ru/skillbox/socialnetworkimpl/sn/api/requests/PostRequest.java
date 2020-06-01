package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PostRequest {
    private String title;
    @JsonProperty("post_text")
    private String postText;
}
