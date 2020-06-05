package ru.skillbox.socialnetworkimpl.sn;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.controllers.ProfileController;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProfileControllerUnitTest {
    @Mock
    private ProfileService service;

    @InjectMocks
    ProfileController controller;

    @Mock
    HttpServletRequest request;

    @Mock
    PersonRequest personRequest;

    @Mock
    PostRequest postRequest;

    @Test
    public void getCurrentUserTest() {
        controller.getCurrentUser(request);
        verify(service).getCurrentUser(request.getSession());
    }

    @Test
    public void editCurrentUserTest() {
        controller.editCurrentUser(request, personRequest);
        verify(service).editCurrentUser(request.getSession(), personRequest);
    }

    @Test
    public void deleteCurrentUserTest() {
        controller.deleteCurrentUser(request);
        verify(service).deleteCurrentUser(request.getSession());
    }

    @Test
    public void getUserByIdTest() {
        controller.getUserById(request, 0);
        verify(service).getUserById(request.getSession(), 0);
    }

    @Test
    public void getPersonsWallPostsTest() {
        controller.getPersonsWallPosts(request, 0, 0, 0);
        verify(service).getPersonsWallPostsByUserId(request.getSession(), 0, 0, 0);
    }

    @Test
    public void addPostToUsersWallTest() {
        controller.addPostToUsersWall(request, 0, 0L, postRequest);
        verify(service).addPostToUsersWall(request.getSession(), 0, 0L, postRequest);
    }

    @Test
    public void searchPersonTest() {
        controller.searchPerson(request, "", "", 0,
                0, 0, 0, 0, 0);
        verify(service).searchPerson(request.getSession(), "", "", 0,
                0, 0, 0, 0, 0);
    }

    @Test
    public void blockUserByIdTest() {
        controller.blockUserById(request, 0);
        verify(service).blockUserById(request.getSession(), 0);
    }

    @Test
    public void unblockUserByIdTest() {
        controller.unblockUserById(request, 0);
        verify(service).unblockUserById(request.getSession(), 0);
    }
}
