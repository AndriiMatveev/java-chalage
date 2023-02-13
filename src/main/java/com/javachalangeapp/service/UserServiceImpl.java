package com.javachalangeapp.service;

import com.javachalangeapp.dto.ApiResponseDto;
import com.javachalangeapp.dto.mapper.UserMapper;
import com.javachalangeapp.model.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Map<Long, User> usersMap = new HashMap<>();
    private static final String userUrl =
            "https://api.stackexchange.com/2.3/users?page="
                    + "%s&pagesize=100&order=asc&min=233"
                    + "&sort=reputation&site=stackoverflow"
                    + "&filter=!-OzjGfzdY6ui0MuJhC1EXRT4w0)J91ZP4";
    private final HttpClient httpClient;
    private final UserMapper userMapper;
    private final TagService tagService;

    public UserServiceImpl(HttpClient httpClient, UserMapper mapper, TagService tagService) {
        this.httpClient = httpClient;
        this.userMapper = mapper;
        this.tagService = tagService;
    }

    @Override
    public void printUsers() {
        int page = 0;
        ApiResponseDto apiResponseDto = null;
        do {
            apiResponseDto = httpClient.get(String.format(userUrl, ++page),
                    ApiResponseDto.class);
            mapUsers(apiResponseDto);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (apiResponseDto.isHasMore() && page < 20);
        Set<User> temp = tagService.putTagsToUsers(usersMap);
        System.out.println("Done");
        temp.forEach(System.out::println);
    }

    private void mapUsers(ApiResponseDto apiResponseDto) {
        List<User> users = Arrays.stream(apiResponseDto.getItems())
                .map(userMapper::parseApiResponseDto)
                .toList();
        users.stream()
                .filter(u -> u.getLocation() != null
                        && (u.getLocation().toLowerCase().contains("moldova")
                        || u.getLocation().toLowerCase().contains("romania")))
                .filter(u -> u.getAnswerCount() != null && u.getAnswerCount() > 0)
                .forEach(u -> usersMap.put(u.getId(), u));
    }
}
