package ru.skillbox.socialnetworkimpl.sn.api.responses.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeUserListResponse {

    @NonNull private Integer likes; // Возможно не int, а String
    private List<String> users; // Возможно не String, а int (судя по Example в api doc).
}
