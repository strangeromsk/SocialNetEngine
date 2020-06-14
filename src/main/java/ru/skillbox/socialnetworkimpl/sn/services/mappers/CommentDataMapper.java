package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;

@Component
public class CommentDataMapper {
    public Integer asInteger(Person person) {
        return person != null ? person.getId() : 0;
    }
    public Integer asInteger(Post post) {
        return post != null ? post.getId() : 0;
    }
    public Integer asInteger(PostComment postComment) {
        return postComment != null ? postComment.getId() : 1;
    }
}
