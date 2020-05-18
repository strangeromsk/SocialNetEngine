package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("me")
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpServletRequest request) {
        return profileService.getCurrentUser(request.getSession());
    }

    @PutMapping("me")
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpServletRequest request,
                                                               @RequestBody PersonEditBody personEditBody) {
        return profileService.editCurrentUser(request.getSession(), personEditBody);
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
    public ResponseEntity<ResponsePlatformApi> getPersonsWallPosts(HttpServletRequest request,
                                                                   @PathVariable int id,
                                                                   @RequestParam int offset,
                                                                   @RequestParam(defaultValue = "20") int itemPerPage) {
        return profileService.getPersonsWallPostsByUserId(request.getSession(), id, offset, itemPerPage);
    }

    @PostMapping(value = "{id}/wall")
    public ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpServletRequest request,
                                                                  @PathVariable("id") int id,
                                                                  @RequestParam long publishDate,
                                                                  @RequestBody PostRequestBody postRequestBody) {
        return profileService.addPostToUsersWall(request.getSession(), id, publishDate, postRequestBody);
    }

    @GetMapping(value = "search/", params = {"first_name", "last_name", "age_from", "age_to",
            "country_id", "city_id", "offset", "itemPerPage"})
    public ResponseEntity<ResponsePlatformApi> searchPerson(HttpServletRequest request,
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
    public ResponseEntity<ResponsePlatformApi> blockUserById(HttpServletRequest request,
                                                             @PathVariable("id") int id) {
        return profileService.blockUserById(request.getSession(), id);
    }

    @DeleteMapping("block/{id}")
    public ResponseEntity<ResponsePlatformApi> unblockUserById(HttpServletRequest request,
                                                               @PathVariable("id") int id) {
        return profileService.unblockUserById(request.getSession(), id);
    }
}