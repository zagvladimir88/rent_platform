package com.zagvladimir.service.user;

import com.zagvladimir.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Page<User> findAll(Pageable page);

    User create(User user, Long locationID) throws MessagingException;

    User findById(Long userId);

    User update(User userToUpdate);

    Long delete(Long id);

    Optional<User> findByLogin(String login);

    boolean activateUser(String code);
}
