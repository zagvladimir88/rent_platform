package com.zagvladimir.service.user;

import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.location.LocationService;
import com.zagvladimir.service.MailSenderService;
import com.zagvladimir.util.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityExistsException;
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
    user.setLocation(locationService.findById(locationId).orElse(null));
    user.setRegistrationDate(new Timestamp(new Date().getTime()));
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    user.setStatus(Status.NOT_ACTIVE);
    user.setActivationCode(UUIDGenerator.generatedUI());
    userRepository.save(user);

    if (userRepository.findUsersByEmail(user.getEmail()).isPresent()) {
      sendEmail(user);
    }
    return userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public User findById(Long userId) {
    return userRepository.findById(userId).orElseThrow(EntityExistsException::new);
  }

  @Transactional
  @Override
  public User update(User userToUpdate) {
    userToUpdate.setModificationDate(new Timestamp(new Date().getTime()));
    userToUpdate.setUserPassword(passwordEncoder.encode(userToUpdate.getUserPassword()));
    return userRepository.save(userToUpdate);
  }

  @Transactional
  @Override
  public Long delete(Long id) {
    userRepository.deleteById(id);
    return id;
  }

  @Override
  public Optional<User> findByLogin(String login) {
    return userRepository.findByUserLogin(login);
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

  private void addRole(User user, Role role) {
    Set<Role> rolesList = new HashSet<>();
    rolesList.add(role);
    user.setRoles(rolesList);
    role.getUsers().add(user);
  }

  private void sendEmail(User user) throws MessagingException {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("recipientName", user.getUsername());
    templateModel.put("email", user.getEmail());
    templateModel.put("url", String.format(ACTIVATION_URL, user.getActivationCode()));

    mailSenderService.sendMessageUsingThymeleafTemplate(
        user.getEmail(), "Activation link", templateModel);
  }
}
