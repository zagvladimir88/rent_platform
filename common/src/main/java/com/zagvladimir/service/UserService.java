package com.zagvladimir.service;

import com.zagvladimir.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User create(User object);

    User findById(Long userId);

    User update(User object);

    Long delete(Long id);

    List<User> search(int limit, int offset);

    Optional<User> findByLogin(String login);

    int createRoleRow(Long userId, Long roleId);
}
