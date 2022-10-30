package com.zagvladimir.service.user;

import com.zagvladimir.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

  List<User> findAll();

  Page<User> findAll(Pageable page);

  User create(User user, Long locationID) throws MessagingException;

  User findById(Long userId);

  User update(User userToUpdate);

  Long delete(Long id);

  User findByLogin(String login);

  boolean activateUser(String code);

  boolean isActivated(String login);

  void confirmItemBooking(Long userID) throws MessagingException;

    Long softDelete(Long userId);
}
