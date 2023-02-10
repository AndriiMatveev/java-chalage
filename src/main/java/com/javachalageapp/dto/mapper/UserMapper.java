package com.javachalageapp.dto.mapper;

import com.javachalageapp.dto.ApiUserDto;
import com.javachalageapp.model.User;

public class UserMapper {
    public User parseApiResponseDto(ApiUserDto dto) {
        User user = new User();
        user.setName(dto.getDisplayName());
        user.setLocation(dto.getLocation());
        user.setAnswerCount(dto.getAnswerCount());
        user.setQuestionCount(dto.getQuestionCount());
        user.setProfile(dto.getLink());
        user.setAvatar(dto.getProfileImage());
        user.setReputation(dto.getReputation());
        user.setId(dto.getUserId());
        return user;
    }
}
