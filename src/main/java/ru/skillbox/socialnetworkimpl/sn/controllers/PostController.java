package ru.skillbox.socialnetworkimpl.sn.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostCommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostCommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.PostServiceImpl;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/post/")
public class PostController {

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class PostInfo {
        private Integer id;
        private String message;

        PostInfo(Integer id) {
            this.id = id;
        }

        PostInfo(String message) {
            this.message = message;
        }
    }

    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<ResponsePlatformApi<List<PostResponse>>> searchPost(@RequestParam String text,
                                                                              @RequestParam(value = "date_from", required = false) Long dateFrom,
                                                                              @RequestParam(value = "date_to", required = false) Long dateTo,
                                                                              @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                                              @RequestParam(value = "itemPerPage", required = false, defaultValue = "20") Integer itemPerPage) {
        log.info("Search Post publication: text='{}'", text);
        List<PostResponse> responsePostApiList = postService.searchPublication(text, dateFrom, dateTo, offset, itemPerPage);
        int total = responsePostApiList != null ? responsePostApiList.size() : 0;
        return new ResponseEntity<>(new ResponsePlatformApi("Search completed.", total, offset, itemPerPage, responsePostApiList), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponsePlatformApi<PostResponse>> getPost(@PathVariable int id) {
        log.info("Search Post publication: ID={}", id);
        PostResponse post = postService.getPublication(id);
        String error = post != null ? "Publication is found" : "Publication not found";
        return new ResponseEntity(ResponsePlatformApi.builder()
                .error(error)
                .timestamp(new Date().getTime())
                .data(post).build(), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<ResponsePlatformApi<List<PostCommentResponse>>> getComments(@PathVariable int id,
                                                                                      @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                                                      @RequestParam(value = "itemPerPage", required = false, defaultValue = "20") Integer itemPerPage) {
        log.info("Get comments for publication: ID={}", id);
        List<PostCommentResponse> commentResponsesList = postService.getComments(id, offset, itemPerPage);
        int total = commentResponsesList != null ? commentResponsesList.size() : 0;
        return new ResponseEntity<>(new ResponsePlatformApi("Search completed.", total, offset, itemPerPage, commentResponsesList), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponsePlatformApi> deletePost(@PathVariable int id) {
        log.info("Delete post: ID={}", id);
        postService.deletePost(id);
        return new ResponseEntity<>(ResponsePlatformApi.builder()
                .error("The publication is removed")
                .data(new PostInfo(id)).build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}/comments/{commentId}")
    public ResponseEntity<ResponsePlatformApi> deleteComment(@PathVariable int id, @PathVariable int commentId) {
        log.info("Delete post comment: ID={} CommentId={}", id, commentId);
        postService.deleteComment(id, commentId);
        return new ResponseEntity<>(ResponsePlatformApi.builder()
                .error("The comment is removed")
                .data(new PostInfo(commentId)).build(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponsePlatformApi> editPost(@PathVariable int id,
                                                        @RequestParam(name = "publish_date", required = false) Long publishDate,
                                                        @RequestBody PostRequest postRequest) {
        log.info("Editing a post: ID={}", id);
        PostResponse post = postService.editPost(id, publishDate, postRequest);
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("The publication was edited").timestamp(new Date().getTime()).data(post).build(), HttpStatus.OK);
    }

    @PutMapping("{id}/recover")
    public ResponseEntity<ResponsePlatformApi> recoverPost(@PathVariable int id) {
        log.info("Restoring a publication: ID={}", id);
        PostResponse post = postService.recoverPost(id);
        return new ResponseEntity(ResponsePlatformApi.builder().error("Publication restored").timestamp(new Date().getTime()).data(post).build(), HttpStatus.OK);
    }

    @PutMapping("{id}/comments/{commentId}")
    public ResponseEntity<ResponsePlatformApi> editComment(@PathVariable int id,
                                                           @PathVariable int commentId,
                                                           @RequestBody PostRequest postRequest) {
        log.info("Editing a comment: ID={}", id);
        PostCommentResponse commentResponse = postService.editComment(id, commentId);
        return new ResponseEntity(ResponsePlatformApi.builder().error("The comment was edited").timestamp(new Date().getTime()).data(commentResponse).build(), HttpStatus.OK);
    }

    @PutMapping("{id}/comments/{commentId}/recover")
    public ResponseEntity<ResponsePlatformApi> recoverComment(@PathVariable int id, @PathVariable int commentId) {
        log.info("Restoring a comment: ID={}", commentId);
        PostCommentResponse post = postService.recoverComment(id, commentId);
        return new ResponseEntity(ResponsePlatformApi.builder().error("Comment restored").timestamp(new Date().getTime()).data(post).build(), HttpStatus.OK);
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<ResponsePlatformApi> createComment(@PathVariable int id, @RequestBody PostCommentRequest postCommentRequest) {
        log.info("Creating a comment for a post: ID={}", id);
        PostCommentResponse comment = postService.createComment(id, postCommentRequest);
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("Creating a comment").timestamp(new Date().getTime()).data(comment).build(), HttpStatus.OK);
    }

    @PostMapping("{id}/report")
    public ResponseEntity<ResponsePlatformApi> reportPost(@PathVariable int id) {
        log.info("The complaint against the publication: ID={}", id);
        postService.reportPost(id);
        return new ResponseEntity<>(ResponsePlatformApi.builder()
                .error("The complaint is accepted for publication")
                .timestamp(new Date().getTime())
                .data(new PostInfo("ok")).build(), HttpStatus.OK);
    }

    @PostMapping("{id}/comments/{commentId}/report")
    public ResponseEntity<ResponsePlatformApi> reportComment(@PathVariable int id, @PathVariable int commentId) {
        log.info("The complaint against the comment: ID={}", commentId);
        postService.reportComment(id, commentId);
        return new ResponseEntity<>(ResponsePlatformApi.builder()
                .error("The complaint is accepted for comment")
                .timestamp(new Date().getTime())
                .data(new PostInfo("ok")).build(), HttpStatus.OK);
    }
}
