package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.TagRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.TagResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Tag;
import ru.skillbox.socialnetworkimpl.sn.repositories.TagRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.TagService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.TagMapper;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagResponse> getTags(String tag, int offset, int itemPerPage) {
        List<Tag> tags = tagRepository.findAllByTagTextContaining(tag, offset, itemPerPage);
        return tagMapper.tagListToTagResponseList(tags);
    }

    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = tagMapper.tagRequestToTag(tagRequest);
        tagRepository.save(tag);
        return tagMapper.tagToTagResponse(tag);
    }

    @Override
    public void deleteTag(int id) {
        tagRepository.deleteById(id);
    }
}
