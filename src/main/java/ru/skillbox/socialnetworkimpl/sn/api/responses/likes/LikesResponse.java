package ru.skillbox.socialnetworkimpl.sn.api.responses.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesResponse {

    @NonNull private Integer likes; // Возможно String судя по док. API
}
