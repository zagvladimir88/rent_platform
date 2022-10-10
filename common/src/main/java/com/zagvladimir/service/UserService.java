package com.zagvladimir.service;

import com.zagvladimir.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Page<User> findAll(Pageable page);

    User create(User object);

    User findById(Long userId);

    User update(User object);

    Long delete(Long id);

    Page<User> search(Pageable page);

    Optional<User> findByLogin(String login);

    int createRoleRow(Long userId, Long roleId);
}
