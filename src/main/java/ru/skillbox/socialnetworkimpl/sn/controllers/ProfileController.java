package ru.skillbox.socialnetworkimpl.sn.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users/")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("me")
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(
            HttpServletRequest request) {
        return profileService.getCurrentUser(request.getSession());
    }

    @PutMapping("me")
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(
            HttpServletRequest request,
            @RequestBody PersonRequest personRequest) {
        return profileService.editCurrentUser(request.getSession(), personRequest);
    }

    @DeleteMapping("me")
    public ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpServletRequest request) {
        return profileService.deleteCurrentUser(request.getSession());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponsePlatformApi> getUserById(HttpServletRequest request,
                                                           @PathVariable("id") int id) {
        return profileService.getUserById(request.getSession(), id);
    }

    @GetMapping(value = "{id}/wall")
    public ResponseEntity<ResponsePlatformApi> getPersonsWallPosts(
            HttpServletRequest request,
            @PathVariable("id") int id,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20", required = false) int itemPerPage) {
        return profileService.getPersonsWallPostsByUserId(request.getSession(), id, offset, itemPerPage);
    }

    @PostMapping(value = "{id}/wall", params = {"publish_date"})
    public ResponseEntity<ResponsePlatformApi> addPostToUsersWall(
            HttpServletRequest request,
            @PathVariable("id") int id,
            @RequestParam(value = "publish_date") long publishDate,
            @RequestBody PostRequest postRequest) {
        return profileService.addPostToUsersWall(request.getSession(), id, publishDate, postRequest);
    }

    @GetMapping(value = "search/", params = {"first_name", "last_name", "age_from", "age_to",
            "country_id", "city_id", "offset"})
    public ResponseEntity<ResponsePlatformApi> searchPerson(
            HttpServletRequest request,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("age_from") int ageFrom,
            @RequestParam("age_to") int ageTo,
            @RequestParam("country_id") int countryId,
            @RequestParam("city_id") int cityId,
            @RequestParam("offset") int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20") int itemPerPage) {
        return profileService.searchPerson(request.getSession(), firstName, lastName, ageFrom,
                ageTo, countryId, cityId, offset, itemPerPage);
    }

    @PutMapping("block/{id}")
    public ResponseEntity<ResponsePlatformApi> blockUserById(
            HttpServletRequest request,
            @PathVariable("id") int id) {
        return profileService.blockUserById(request.getSession(), id);
    }

    @DeleteMapping("block/{id}")
    public ResponseEntity<ResponsePlatformApi> unblockUserById(
            HttpServletRequest request,
            @PathVariable("id") int id) {
        return profileService.unblockUserById(request.getSession(), id);
    }
}