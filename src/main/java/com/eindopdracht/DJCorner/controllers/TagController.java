package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.dtos.TagRequestDto;
import com.eindopdracht.DJCorner.dtos.TagResponseDto;
import com.eindopdracht.DJCorner.helpers.UriHelper;
import com.eindopdracht.DJCorner.mappers.TagMapper;
import com.eindopdracht.DJCorner.models.Tag;
import com.eindopdracht.DJCorner.services.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagByID(@PathVariable Long id) {
        return ResponseEntity.ok(TagMapper.toTagResponseDto(this.tagService.getTagById(id)));
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagRequestDto tagRequestDto) {
        Tag tag = this.tagService.createTag(tagRequestDto);
        TagResponseDto tagResponseDto = TagMapper.toTagResponseDto(tag);

        URI uri = UriHelper.buildResourceUri(tag.getId());

        return ResponseEntity.created(uri).body(tagResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(@PathVariable Long id, @Valid @RequestBody TagRequestDto tagRequestDto) {
        TagResponseDto updatedTag = tagService.updateTag(id, tagRequestDto);

        return ResponseEntity.ok(updatedTag);
    }
}
