package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequest {
    @JsonProperty("parent_id")
    private int parentId;
    @JsonProperty("comment_text")
    private String commentText;
}
