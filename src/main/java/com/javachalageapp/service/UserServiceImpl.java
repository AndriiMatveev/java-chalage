package com.javachalageapp.service;

import com.javachalageapp.dto.ApiResponseDto;
import com.javachalageapp.dto.ApiResponseTagsDto;
import com.javachalageapp.dto.mapper.TagMapper;
import com.javachalageapp.dto.mapper.UserMapper;
import com.javachalageapp.model.Tag;
import com.javachalageapp.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Map<Long, User> usersMap = new HashMap<>();
    private int page = 10;
    private String url =
            "https://api.stackexchange.com/2.3/users?page="
                    + (page) + "&pagesize=100&order=asc&min=233"
                    + "&sort=reputation&site=stackoverflow"
                    + "&filter=!-OzjGfzdY6ui0MuJhC1EXRT4w0)J91ZP4";
    private final HttpClient httpClient;
    private final UserMapper userMapper;

    private final TagMapper tagMapper;

    public UserServiceImpl(HttpClient httpClient, UserMapper mapper, TagMapper tagMapper) {
        this.httpClient = httpClient;
        this.userMapper = mapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public void printUsers() {
        ApiResponseDto apiResponseDto = httpClient.get(url, ApiResponseDto.class);
        mapUsers(apiResponseDto);
        while (apiResponseDto.isHasMore() && page < 25) {
            url = "https://api.stackexchange.com/2.3/users?page="
                    + (++page) + "&&pagesize=100&order=asc&min=233&"
                    + "sort=reputation&site=stackoverflow"
                    + "&filter=!-OzjGfzdY6ui0MuJhC1EXRT4w0)J91ZP4";
            apiResponseDto = httpClient.get(url, ApiResponseDto.class);
            mapUsers(apiResponseDto);
        }
        List<User> temp = putTagsToUsers(usersMap);
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

    private List<User> putTagsToUsers(Map<Long, User> usersMap) {
        Map<Long, User> tempMap = usersMap;
        int page = 1;
        String ids = usersMap.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining(";"));
        String url = "https://api.stackexchange.com/2.3/users/"
                + ids + "/tags?page=" + page
                + "&pagesize=100&order=desc&sort=popular&site=stackoverflow&filter=!nFi2nv)eiG";
        ApiResponseTagsDto apiTagsDto = httpClient.get(url, ApiResponseTagsDto.class);
        List<Tag> tags = new ArrayList<>();
        tags = Arrays.stream(apiTagsDto.getItems())
                .map(tagMapper::parseApiTagsDto)
                .toList();
        while (apiTagsDto.isHasMore()) {
            url = "https://api.stackexchange.com/2.3/users/"
                    + ids + "/tags?page=" + ++page
                    + "&pagesize=100&order=desc&sort=popular&site=stackoverflow&filter=!nFi2nv)eiG";
            apiTagsDto = httpClient.get(url, ApiResponseTagsDto.class);
            tags.addAll(Arrays.stream(apiTagsDto.getItems())
                    .map(tagMapper::parseApiTagsDto)
                    .toList());
        }
        Set<Long> idsWithTags = tags.stream()
                .filter(t -> t.getName().equalsIgnoreCase("java")
                        || t.getName().equalsIgnoreCase(".net")
                        || t.getName().equalsIgnoreCase("docker")
                        || t.getName().equalsIgnoreCase("C#"))
                .map(Tag::getUserId)
                .collect(Collectors.toSet());
        List<Tag> usersTags = tags.stream()
                .filter(t -> idsWithTags.contains(t.getUserId()))
                .toList();
        for (Tag tag : usersTags) {
            Long id = tag.getUserId();
            if (tempMap.containsKey(id)) {
                User user = tempMap.get(id);
                if (user.getTags() == null) {
                    user.setTags(tag.getName());
                } else {
                    user.setTags(user.getTags() + ", "
                            + tag.getName());
                }
                tempMap.put(id, user);
            }
        }
        return tempMap.values().stream()
                .filter(u -> u.getTags() != null)
                .toList();
    }
}
