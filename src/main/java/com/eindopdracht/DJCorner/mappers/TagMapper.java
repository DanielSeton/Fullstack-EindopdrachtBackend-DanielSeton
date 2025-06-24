package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.TagRequestDto;
import com.eindopdracht.DJCorner.dtos.TagResponseDto;
import com.eindopdracht.DJCorner.models.Tag;

public class TagMapper {

    public static Tag toEntity(TagRequestDto tagRequestDto) {
        Tag tag = new Tag();

        tag.setName(tagRequestDto.getName());

        return tag;
    }

    public static TagResponseDto toTagResponseDto(Tag tag) {
        TagResponseDto tagResponseDto = new TagResponseDto();

        tagResponseDto.id = tag.getId();
        tagResponseDto.name = tag.getName();

        return tagResponseDto;
    }
}
