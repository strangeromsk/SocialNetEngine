package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.TagRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.api.responses.TagResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Tag;
import ru.skillbox.socialnetworkimpl.sn.repositories.TagRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.TagService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.TagMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public ResponsePlatformApi getTags(String tag, int offset, int itemPerPage) {
        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);
        Page<Tag> tags = tagRepository.findAllByTagContainingIgnoreCase(tag, pageable);
        List<TagResponse> listTag = tags.stream().map(tagMapper::tagToTagResponse).collect(Collectors.toList());
        return new ResponsePlatformApi("Search for a tag", (int) tags.getTotalElements(),offset,itemPerPage,listTag);
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
