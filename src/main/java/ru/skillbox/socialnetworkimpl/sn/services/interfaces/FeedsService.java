package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;

import java.util.List;

public interface FeedsService {
    List<PostResponse> getFeeds(String name, int offset, int itemPerPage);
}
