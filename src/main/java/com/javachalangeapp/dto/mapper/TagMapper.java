package com.javachalangeapp.dto.mapper;

import com.javachalangeapp.dto.ApiTagsDto;
import com.javachalangeapp.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag parseApiTagsDto(ApiTagsDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setUserId(dto.getUserId());
        return tag;
    }
}
