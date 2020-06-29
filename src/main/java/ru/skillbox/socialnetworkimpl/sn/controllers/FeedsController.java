package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.FeedsService;

import java.util.List;

@RestController
public class FeedsController {
    private FeedsService feedsService;

    @Autowired
    public FeedsController(FeedsService feedsService) {
        this.feedsService = feedsService;
    }

    @GetMapping("/api/v1/feeds")
    public ResponseEntity<ResponsePlatformApi<List<PostResponse>>> getFeeds(@RequestParam(defaultValue = "") String name,
                                                                            @RequestParam(defaultValue = "0") Integer offset,
                                                                            @RequestParam(defaultValue = "20") Integer itemPerPage) {
        List<PostResponse> feeds = feedsService.getFeeds(name, offset, itemPerPage);
        int total = (feeds == null) ? 0 : feeds.size();
        return new ResponseEntity<>(
                new ResponsePlatformApi<>("string", total, offset, itemPerPage, feeds), HttpStatus.OK);
    }
}
