package com.zagvladimir.service.user;

import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.user.User;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.location.LocationService;
import com.zagvladimir.service.mail.MailSenderService;
import com.zagvladimir.util.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String ACTIVATION_URL =
      "http://localhost:8080/api/registration/activate/%s/";
  private final UserRepository userRepository;
  private final LocationService locationService;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final MailSenderService mailSenderService;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Page<User> findAll(Pageable page) {
    return userRepository.findAll(page);
  }

  @Transactional
  @Override
  public User create(User user, Long locationId) throws MessagingException {

    addRole(user, roleRepository.findRoleByName("ROLE_USER"));
    user.setLocation(locationService.findById(locationId));
    user.setRegistrationDate(new Timestamp(new Date().getTime()));
    user.getCredentials().setUserPassword(passwordEncoder.encode(user.getCredentials().getUserPassword()));

    user.setStatus(Status.NOT_ACTIVE);
    user.setActivationCode(UUIDGenerator.generatedUI());
    userRepository.save(user);

    if (userRepository.findUsersByCredentials_Email(user.getCredentials().getEmail()).isPresent()) {
      sendEmail(user);
    }
    return userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public User findById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The user with id:%d not found", userId)));
  }

  @Transactional
  @Override
  public User update(User userToUpdate) {
    userToUpdate.setModificationDate(new Timestamp(new Date().getTime()));
    userToUpdate.getCredentials().setUserPassword(passwordEncoder.encode(userToUpdate.getCredentials().getUserPassword()));
    userRepository.save(userToUpdate);
    return userRepository.findById(userToUpdate.getId()).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  @Override
  public Long delete(Long id) {
    userRepository.deleteById(id);
    return id;
  }

  @Override
  public User findByLogin(String login) {
    return userRepository
        .findByCredentialsUserLogin(login)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The user with login: %s not found", login)));
  }

  @Override
  public boolean activateUser(String code) {
    Optional<User> user = userRepository.findUsersByActivationCode(code);
    if (user.isPresent() && user.get().getStatus().equals(Status.NOT_ACTIVE)) {
      user.get().setStatus(Status.ACTIVE);
      userRepository.save(user.get());
      return true;
    }
    return false;
  }

  @Override
  public boolean isActivated(String login) {
    Optional<User> user = userRepository.findByCredentialsUserLogin(login);
    return user.map(value -> value.getStatus().equals(Status.ACTIVE)).orElse(false);
  }

  private void addRole(User user, Role role) {
    Set<Role> rolesList = new HashSet<>();
    rolesList.add(role);
    user.setRoles(rolesList);
    role.getUsers().add(user);
  }

  private void sendEmail(User user) throws MessagingException {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("recipientName", user.getFirstName());
    templateModel.put("email", user.getCredentials().getEmail());
    templateModel.put("url", String.format(ACTIVATION_URL, user.getActivationCode()));

    mailSenderService.sendMessageUsingThymeleafTemplate(
            user.getCredentials().getEmail(), "Activation link", templateModel);
  }
}
