package com.zagvladimir.service.user;

import com.zagvladimir.dto.requests.users.UserChangeAddressRequest;
import com.zagvladimir.dto.requests.users.UserChangeCredentialsRequest;
import com.zagvladimir.dto.requests.users.UserCreateRequest;
import com.zagvladimir.dto.requests.users.UserUpdateRequest;
import com.zagvladimir.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

  List<UserResponse> findAll();

  Page<UserResponse> findAll(Pageable page);

  UserResponse create(UserCreateRequest request) throws MessagingException;

  UserResponse findById(Long userId);

  UserResponse update(UserUpdateRequest userToUpdate, Long id);
  UserResponse update(UserChangeCredentialsRequest userToUpdate, Long id);
  UserResponse update(UserChangeAddressRequest userToUpdate, Long id);

  Long delete(Long id);

  UserResponse findByLogin(String login);

  boolean activateUser(String code);

  boolean isActivated(String login);

  void confirmItemBooking(Long userID) throws MessagingException;

    Long softDelete(Long userId);
}
