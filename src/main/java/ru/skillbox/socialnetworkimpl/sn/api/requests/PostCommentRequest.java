package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCommentRequest {
    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("comment_text")
    private String commentText;
}
