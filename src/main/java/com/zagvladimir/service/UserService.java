package com.zagvladimir.service;

import com.zagvladimir.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> findAll();

    Map<String, Object> getUserStats();

    User create(User object);

    User findById(Long userId);

    User update(User object);

    Long delete(Long id);
}
