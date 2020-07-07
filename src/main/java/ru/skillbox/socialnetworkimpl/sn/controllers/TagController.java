package ru.skillbox.socialnetworkimpl.sn.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.TagRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ReportApi;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.TagServiceImpl;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/tags/")
public class TagController {
    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ResponsePlatformApi> getTags(@RequestParam(value = "tag", defaultValue = "") String tag,
                                                       @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                       @RequestParam(value = "itemPerPage", required = false, defaultValue = "20") Integer itemPerPage) {
        log.info("Search for a tag by line: {}", tag);
        return new ResponseEntity<>(tagService.getTags(tag, offset, itemPerPage), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponsePlatformApi> createTag(@RequestBody TagRequest tagRequest) {
        ResponsePlatformApi ps = ResponsePlatformApi.builder()
                .timestamp(new Date().getTime())
                .error("A new tag was created")
                .data(tagService.createTag(tagRequest))
                .build();
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponsePlatformApi> deleteTag(@RequestParam(value = "id") int id) {
        tagService.deleteTag(id);
        ReportApi report = new ReportApi("ok");
        ResponsePlatformApi ps = ResponsePlatformApi.builder()
                .timestamp(new Date().getTime())
                .error("The tag is removed")
                .data(report)
                .build();
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }
}