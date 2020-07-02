package ru.skillbox.socialnetworkimpl.sn.services;

import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Friendship;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;
import ru.skillbox.socialnetworkimpl.sn.repositories.FriendshipRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostCommentRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.FeedsService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.CommentMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PostMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedsServiceImpl implements FeedsService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommentRepository commentRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<PostResponse> getFeeds(String name, int offset, int itemPerPage) {
        Person currentUser = getCurrentUser();
        //Получить id друзей текущего пользователя
        List<Friendship> friends = friendshipRepository.getFriendsForUserById(currentUser.getId());
        List<Integer> friendsIds = new ArrayList<>();
        friends.forEach(friend -> {
            if (currentUser.getId() != friend.getSrcPersonId()) {
                friendsIds.add(friend.getSrcPersonId());
            } else {
                friendsIds.add(friend.getDstPersonId());
            }
        });

        //Получить посты по id друзей
        List<Post> posts = postRepository.getFriendsPosts(friendsIds, name, offset, itemPerPage);
        List<PostResponse> feeds = new ArrayList<>();
        posts.forEach(post -> {
            List<PostComment> comments = commentRepository.findAllByPostId(post);
            List<CommentResponse> commentResponses = comments.stream().map(commentMapper::commentToCommentResponse).collect(Collectors.toList());
            PostResponse postResponse = postMapper.postToPostResponse(post);
            postResponse.setCommentResponses(commentResponses);
            feeds.add(postResponse);
        });
        return feeds;
    }

    private Person getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return personRepository.findByEmail(userEmail);
    }
}
