package com.javachalageapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseDto {
    @JsonProperty("has_more")
    private boolean hasMore;
    private ApiUserDto[] items;
}
