package ru.skillbox.socialnetworkimpl.sn.api.responses.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsLikedResponse {

    private boolean likes;
}
