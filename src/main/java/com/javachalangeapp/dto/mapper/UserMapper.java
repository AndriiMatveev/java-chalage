package com.javachalangeapp.dto.mapper;

import com.javachalangeapp.dto.ApiUserDto;
import com.javachalangeapp.model.User;
import org.springframework.stereotype.Component;

@Component
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
