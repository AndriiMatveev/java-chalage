package com.javachalangeapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiTagsDto {
    @JsonProperty("user_id")
    private Long userId;
    private String name;
}
