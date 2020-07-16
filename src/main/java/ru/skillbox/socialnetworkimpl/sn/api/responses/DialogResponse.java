package ru.skillbox.socialnetworkimpl.sn.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogResponse {

    private Integer id;
    private String link;
    private Integer count;
    private Boolean online;

    @JsonProperty("last_activity")
    private LocalDateTime lastActivity;

    @JsonProperty("unread_count")
    private Integer unreadCount;

    @JsonProperty("last_message")
    private MessageResponse lastMessage;

    @JsonProperty("user_ids")
    private ArrayList<Integer> userIds;

    @JsonProperty("message_id")
    private Integer messageId;
}
