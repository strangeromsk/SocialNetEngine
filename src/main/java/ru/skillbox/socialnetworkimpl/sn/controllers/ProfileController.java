package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.*;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("me")
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> getCurrentUser(
            HttpServletRequest request) {
        return profileService.getCurrentUser(request.getSession());
    }

    @PutMapping("me")
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> editCurrentUser(
            HttpServletRequest request,
            @RequestBody PersonEditBody personEditBody) {
        return profileService.editCurrentUser(request.getSession(), personEditBody);
    }

    @DeleteMapping("me")
    public ResponseEntity<ResponsePlatformApi<MessageResponse>> deleteCurrentUser(
            HttpServletRequest request) {
        return profileService.deleteCurrentUser(request.getSession());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> getUserById(
            HttpServletRequest request,
            @PathVariable("id") long id) {
        return profileService.getUserById(request.getSession(), id);
    }

    @GetMapping(value = "{id}/wall", params = {"offset", "itemPerPage"})
    public ResponseEntity<ResponsePlatformApi<List<PersonWallPostResponse>>> getPersonsWallPosts(
            HttpServletRequest request,
            @PathVariable("id") long id,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "itemPerPage") int itemPerPage) {  // TODO Добавить default = 20
        return null;
    }

    @PostMapping(value = "{id}/wall", params = {"publish_date"})
    public ResponseEntity<ResponsePlatformApi<PostResponse>> addPostToUsersWall(
            HttpServletRequest request,
            @PathVariable("id") long id,
            @RequestParam(value = "publish_date") int publishDate,
            @RequestBody PostRequestBody postRequestBody) {
        return null;
    }

    @GetMapping(value = "search/", params = {"first_name", "last_name", "age_from", "age_to",
            "country_id", "city_id", "offset", "itemPerPage"})
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> searchPerson(
            HttpServletRequest request,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("age_from") int ageFrom,
            @RequestParam("age_to") int ageTo,
            @RequestParam("country_id") int countryId,
            @RequestParam("city_id") int cityId,
            @RequestParam("offset") int offset,
            @RequestParam("itemPerPage") int itemPerPage) { // TODO Добавить default = 20
        return profileService.searchPerson(request.getSession(), firstName, lastName, ageFrom,
                ageTo, countryId, cityId, offset, itemPerPage);

    }

    @PutMapping("block/{id}")
    public ResponseEntity<ResponsePlatformApi<MessageResponse>> blockUserById(
            HttpServletRequest request,
            @PathVariable("id") long id) {
        return profileService.blockUserById(request.getSession(), id);
    }

    @DeleteMapping("block/{id}")
    public ResponseEntity<ResponsePlatformApi<MessageResponse>> unblockUserById(
            HttpServletRequest request,
            @PathVariable("id") long id) {
        return profileService.unblockUserById(request.getSession(), id);
    }
}