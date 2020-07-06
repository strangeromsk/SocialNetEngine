package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetworkimpl.sn.api.requests.TagRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.TagResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Tag;

import java.util.List;

@Mapper
public interface TagMapper {
    Tag tagRequestToTag(TagRequest tagRequest);
    TagResponse tagToTagResponse(Tag tag);
    List<TagResponse> tagListToTagResponseList(List<Tag> tags);
}
