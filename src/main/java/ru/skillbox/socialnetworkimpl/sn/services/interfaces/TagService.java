package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import ru.skillbox.socialnetworkimpl.sn.api.requests.TagRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.TagResponse;
import java.util.List;

public interface TagService {
    // поиск по тегам
    List<TagResponse> getTags(String tag, int offset, int itemPerPage);

    // создание тега
    TagResponse createTag(TagRequest tagRequest);

    // удаление тега
    void deleteTag(int id);

}
