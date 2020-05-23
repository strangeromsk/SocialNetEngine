package ru.skillbox.socialnetworkimpl.sn.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.PostType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonWallPostResponse extends PostResponse {

    private PostType type;
}
