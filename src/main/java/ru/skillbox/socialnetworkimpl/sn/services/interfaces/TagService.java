package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import ru.skillbox.socialnetworkimpl.sn.api.requests.TagRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.api.responses.TagResponse;

public interface TagService {
    // поиск по тегам
    ResponsePlatformApi getTags(String tag, int offset, int itemPerPage);
    // создание тега
    TagResponse createTag(TagRequest tagRequest);
    // удаление тега
    void deleteTag(int id);
}
