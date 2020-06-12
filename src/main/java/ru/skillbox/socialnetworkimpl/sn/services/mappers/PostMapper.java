package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;

import java.util.List;

@Mapper(
        uses = {
                DataMapper.class,
                PersonMapper.class,
                PostCommentMapper.class
        }
)

public interface PostMapper {
    PostResponse postToPostResponse(Post post);
    List<PostResponse> postToPostResponse(List<Post> posts);
    Post requestPostToPost(PostRequest requestPost);
}
