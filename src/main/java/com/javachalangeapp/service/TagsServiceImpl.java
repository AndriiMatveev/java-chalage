package com.javachalangeapp.service;

import com.javachalangeapp.dto.ApiResponseTagsDto;
import com.javachalangeapp.dto.mapper.TagMapper;
import com.javachalangeapp.model.Tag;
import com.javachalangeapp.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TagsServiceImpl implements TagService {
    private static final String tagsUrl = "https://api.stackexchange.com/2.3/users/"
            + "%s/tags?page=%s"
            + "&pagesize=100&order=desc&sort=popular"
            + "&site=stackoverflow&filter=!nFi2nv)eiG";
    private final HttpClient httpClient;
    private final TagMapper tagMapper;

    public TagsServiceImpl(HttpClient httpClient, TagMapper tagMapper) {
        this.httpClient = httpClient;
        this.tagMapper = tagMapper;
    }

    @Override
    public Set<User> putTagsToUsers(Map<Long, User> usersMap) {
        Map<Long, User> tempMap = usersMap;
        String ids = usersMap.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining(";"));
        ApiResponseTagsDto apiTagsDto = null;
        ArrayList<Tag> tags = new ArrayList<>();
        int page = 0;
        do {
            apiTagsDto = httpClient.get(String.format(tagsUrl, ids, ++page),
                    ApiResponseTagsDto.class);
            tags.addAll(Arrays.stream(apiTagsDto.getItems())
                    .map(tagMapper::parseApiTagsDto)
                    .toList());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (apiTagsDto.isHasMore());
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
                .collect(Collectors.toSet());
    }
}
