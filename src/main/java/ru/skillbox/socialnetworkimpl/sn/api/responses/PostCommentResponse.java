package ru.skillbox.socialnetworkimpl.sn.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentResponse {
    private int id;
    private long time;
    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("author_id")
    private int authorId;
    @JsonProperty("comment_text")
    private String commentText;
    @JsonProperty("is_blocked")
    private boolean isBlocked;
}