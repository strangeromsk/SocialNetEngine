package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CommentResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;

import java.util.List;

@Mapper(
        uses = {
                DataMapper.class,
                CommentDataMapper.class
        }
)

public interface CommentMapper {

//    @Mapping(target = "parentId", ignore = true)
//    @Mapping(target = "post.id", source = "postId")
//    @Mapping(target = "author.id", source = "authorId")
    CommentResponse commentToCommentResponse(PostComment postComment);
    List<CommentResponse> commentToCommentResponse(List<PostComment> postComment);

//    @InheritInverseConfiguration
  //  PostComment commentRequestToPostComment(PostCommentRequest postCommentRequest);
    //List<CommentApi> commentToCommentApi(List<PostComment> posts);
}