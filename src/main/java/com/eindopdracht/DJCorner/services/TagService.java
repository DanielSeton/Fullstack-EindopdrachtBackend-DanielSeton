package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.TagRequestDto;
import com.eindopdracht.DJCorner.dtos.TagResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.TagMapper;
import com.eindopdracht.DJCorner.models.Tag;
import com.eindopdracht.DJCorner.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagResponseDto> getAllTags() {
        List<Tag> tagsList = tagRepository.findAll();
        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();

        for (Tag tag : tagsList) {
            TagResponseDto tagResponseDto = TagMapper.toTagResponseDto(tag);
            tagResponseDtoList.add(tagResponseDto);
        }
        return tagResponseDtoList;
    }

    public Tag getTagById(Long id) {
        return this.tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag with id: " + id + " not found"));
    }

    public Tag createTag(TagRequestDto tagRequestDto) {
        return this.tagRepository.save(TagMapper.toEntity(tagRequestDto));
    }

    public void deleteTag(Long id) {
        Optional<Tag> optionalTag = this.tagRepository.findById(id);

        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException("Tag with id: " + id + " not found");
        }

        tagRepository.deleteById(id);
    }

    public TagResponseDto updateTag(Long id, TagRequestDto tagRequestDto) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag with id: " + id + " not found"));

        tag.setName(tagRequestDto.getName());

        Tag updatedTag = this.tagRepository.save(tag);

        return TagMapper.toTagResponseDto(updatedTag);
    }
}
