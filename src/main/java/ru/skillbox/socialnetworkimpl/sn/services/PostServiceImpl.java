package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostCommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CountryResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostCommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;
import ru.skillbox.socialnetworkimpl.sn.repositories.CommentRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.CountryRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.PostService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PostCommentMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PostMapper;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * заглушка Post Service
 */
@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private PostMapper postMapper;
    private CountryRepository countryRepository;
    private PostCommentMapper postCommentMapper;


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
        }
        return ps;
    }

    @Override
    public PostResponse getPublication(int id) {
        Post post = postRepository.getOne(id);
        PostResponse ps = postMapper.postToPostResponse(post);
        setCountry(post, ps);
        return ps;
    }

    @Override
    public List<PostCommentResponse> getComments(int id, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public void deletePost(int id) {
        Post post = postRepository.getOne(id);
        if (post.isBlocked()) {
            throw new EntityNotFoundException("Post is already blocked");
        }
        post.setBlocked(true);
        postRepository.save(post);
    }

    @Override
    public void deleteComment(int id, int commentId) {

    }

    @Override
    public PostResponse editPost(int id, Long publishDate, PostRequest postRequest) {
        return null;
    }

    @Override
    public PostResponse recoverPost(int id) {
        return null;
    }

    @Override
    public PostCommentResponse editComment(int id, int commentId) {
        return null;
    }

    @Override
    public PostCommentResponse recoverComment(int id, int commentId) {
        return null;
    }

    @Override
    public PostCommentResponse createComment(int id, PostCommentRequest postCommentRequest) {
        Post post = postRepository.getOne(id);
        //PostComment postComment = postCommentMapper.commentRequestToPostComment(postCommentRequest);
        PostComment postComment = new PostComment();

                                    postComment.setPostId(post);

                                    log.info("Author: {} ", post.getAuthor().getId());
                                    postComment.setAuthorId(post.getAuthor());


        PostComment parent = commentRepository.findById(postCommentRequest.getParentId()).orElse(null);
        log.info("Parent {}", parent.getPostId().getId());
                                    postComment.setParentId(parent);

            if (postCommentRequest != null) log.info("Comment text: {}", postCommentRequest.getCommentText());
            else log.info("Trable!!!!!");
                                    postComment.setCommentText(postCommentRequest.getCommentText());
        LocalDateTime time = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
                                    postComment.setTime(time);



        commentRepository.save(postComment);
        PostCommentResponse ps = postCommentMapper.postCommentToCommentResponse(postComment);
        return null;
    }

    @Override
    public void reportPost(int id) {

    }

    @Override
    public void reportComment(int id, int commentId) {

    }

    void setCountry(Post post, PostResponse postResponse) {
        Country country = countryRepository.getOne(post.getAuthor().getTown().getCountryId());
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        postResponse.getAuthor().setCountry(countryResponse);
    }
}
