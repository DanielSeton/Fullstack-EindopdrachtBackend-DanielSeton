package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.TagRequestDto;
import com.eindopdracht.DJCorner.dtos.TagResponseDto;
import com.eindopdracht.DJCorner.models.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public static Tag toEntity(TagRequestDto tagRequestDto) {
        Tag tag = new Tag();

        tag.setName(tagRequestDto.getName());

        return tag;
    }

    public static TagResponseDto toTagResponseDto(Tag tag) {
        TagResponseDto tagResponseDto = new TagResponseDto();

        tagResponseDto.setId(tag.getId());
        tagResponseDto.setName(tag.getName());

        return tagResponseDto;
    }
}
