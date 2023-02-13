package com.javachalangeapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiUserDto {
    @JsonProperty("display_name")
    private String displayName;
    private String location;
    @JsonProperty("answer_count")
    private Long answerCount;
    @JsonProperty("question_count")
    private Long questionCount;
    private String link;
    @JsonProperty("profile_image")
    private String profileImage;
    private Long reputation;
    @JsonProperty("user_id")
    private Long userId;
}
