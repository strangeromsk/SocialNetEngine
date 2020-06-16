package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostCommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CountryResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostCommentRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.CountryRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.PostService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.CommentMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PostMapper;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final PostMapper postMapper;
    private final CountryRepository countryRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<PostResponse> searchPublication(String text, long dateFrom, long dateTo, int offset, int itemPerPage) {
        List<Post> posts = postRepository.findAllByPostTextContainingAndTimeBetween(text, dateFrom, dateTo)
                .stream().skip(offset).limit(itemPerPage).collect(Collectors.toList());
        if (posts.isEmpty()) {
            throw new EntityNotFoundException("Search by parameters empty");
        }
        List<PostResponse> ps = postMapper.postToPostResponse(posts);
        for (int i = 0; i < posts.size(); i++) {
            setCountry(posts.get(i), ps.get(i));
            List<PostComment> postComments = commentRepository.findAllByPostId(postRepository.getOne(posts.get(i).getId()));
            List<CommentResponse> list = null;
            if (!postComments.isEmpty()) {
                list = postComments.stream().map(commentMapper::commentToCommentResponse).collect(Collectors.toList());
            }
            ps.get(i).setCommentResponses(list);
        }
        return ps;
    }

    @Override
    @Transactional
    public PostResponse getPublication(int id) {
        Post post = postRepository.getOne(id);
        PostResponse ps = postMapper.postToPostResponse(post);
        setCountry(post, ps);
        List<PostComment> comment = commentRepository.findAllByPostId(post);
        List<CommentResponse> list = comment.stream().map(commentMapper::commentToCommentResponse).collect(Collectors.toList());
        ps.setCommentResponses(list);
        return ps;
    }

    @Override
    public List<CommentResponse> getComments(int id, int offset, int itemPerPage) {
        List<PostComment> postComments = commentRepository.findAllByPostId(postRepository.getOne(id));
        if (postComments.isEmpty()) {
            throw new EntityNotFoundException("comments to this publication were not found");
        }
        return postComments.stream().skip(offset)
                .limit(itemPerPage)
                .map(commentMapper::commentToCommentResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        Post post = postRepository.getOne(id);
        post.setDeleted(true);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void deleteComment(int id, int commentId) {
        PostComment postComment = commentRepository.getOne(commentId);
        postComment.setDeleted(true);
        commentRepository.save(postComment);
    }

    @Override
    @Transactional
    public PostResponse editPost(int id, Long publishDate, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
        post.setPostText(postRequest.getPostText());
        post.setTitle(postRequest.getTitle());
        List<PostComment> postComments = post.getComments();
        post.setTime(Instant.ofEpochMilli(publishDate).atZone(ZoneId.systemDefault()).toLocalDate());
        postRepository.save(post);
        PostResponse ps = postMapper.postToPostResponse(post);
        ps.setCommentResponses(commentMapper.commentToCommentResponse(postComments));
        return ps;
    }

    @Override
    public PostResponse recoverPost(int id) {
        return null;
    }

    @Override
    @Transactional
    public CommentResponse editComment(int id, int commentId, PostCommentRequest commentRequest) {
        PostComment postComment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Comment not found"));
        postComment.setPostId(postRepository.getOne(id));
        postComment.setParentId(commentRepository.getOne(commentRequest.getParentId()));
        postComment.setCommentText(commentRequest.getCommentText());
        commentRepository.save(postComment);
        return commentMapper.commentToCommentResponse(postComment);
    }

    @Override
    public CommentResponse recoverComment(int id, int commentId) {
        return null;
    }

    @Override
    public CommentResponse createComment(int id, PostCommentRequest postCommentRequest) {
        Post post = postRepository.getOne(id);
        PostComment postComment = createPostComment(post, postCommentRequest);
        commentRepository.save(postComment);
        return commentMapper.commentToCommentResponse(postComment);
    }

    @Override
    public void reportPost(int id) {

    }

    @Override
    public void reportComment(int id, int commentId) {

    }

    private void setCountry(Post post, PostResponse postResponse) {
        Country country = countryRepository.getOne(post.getAuthor().getTown().getCountryId());
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        postResponse.getAuthor().setCountry(countryResponse);
    }

    private PostComment createPostComment(Post post, PostCommentRequest ps) {
        PostComment postComment = new PostComment();
        postComment.setPostId(post);
        postComment.setAuthorId(post.getAuthor());
        PostComment parent = null;
        if (ps.getParentId() != null)  {
            parent = commentRepository.findById(ps.getParentId()).orElseThrow(() -> new EntityNotFoundException("The parent message is missing"));
        }
        postComment.setParentId(parent);
        postComment.setCommentText(ps.getCommentText());
        LocalDateTime time = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        postComment.setTime(time);
        return postComment;
    }
}
