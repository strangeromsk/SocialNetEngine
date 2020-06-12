package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostCommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostCommentResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;

@Mapper(
        uses = {
                DataMapper.class,
                PersonMapper.class
        }
)

public interface PostCommentMapper {

    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "post.id", source = "postId")
    @Mapping(target = "author.id", source = "authorId")
    PostCommentResponse postCommentToCommentResponse(PostComment postComment);

//    @InheritInverseConfiguration
  //  PostComment commentRequestToPostComment(PostCommentRequest postCommentRequest);
    //List<CommentApi> commentToCommentApi(List<PostComment> posts);
}