package com.javachalageapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseTagsDto {
    @JsonProperty("has_more")
    private boolean hasMore;
    private ApiTagsDto[] items;
}
