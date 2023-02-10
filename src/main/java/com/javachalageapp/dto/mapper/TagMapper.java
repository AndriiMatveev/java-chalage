package com.javachalageapp.dto.mapper;

import com.javachalageapp.dto.ApiTagsDto;
import com.javachalageapp.model.Tag;

public class TagMapper {
    public Tag parseApiTagsDto(ApiTagsDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setUserId(dto.getUserId());
        return tag;
    }
}
