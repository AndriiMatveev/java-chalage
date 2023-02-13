package com.javachalangeapp.service;

import com.javachalangeapp.model.User;
import java.util.Map;
import java.util.Set;

public interface TagService {

    Set<User> putTagsToUsers(Map<Long, User> usersMap);
}
