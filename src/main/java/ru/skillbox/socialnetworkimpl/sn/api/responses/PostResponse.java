package ru.skillbox.socialnetworkimpl.sn.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.PostType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private int id;
    private long time;
    private PersonResponse author;
    private String title;

    @JsonProperty("post_text")
    private String postText;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    private int likes;
    private List<CommentResponse> comments;
}
