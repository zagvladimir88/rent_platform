package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.LocationService;
import com.zagvladimir.service.MailSenderService;
import com.zagvladimir.service.UserService;
import com.zagvladimir.util.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityExistsException;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final LocationService locationService;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final MailSenderService mailSenderService;

  @Transactional
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
  public User create(User user, Long locationId) {

    addRole(user,roleRepository.findRoleByName("ROLE_USER"));
    user.setLocation(locationService.findById(locationId).orElse(null));

    user.setRegistrationDate(new Timestamp(new Date().getTime()));
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    user.setStatus(Status.NOT_ACTIVE);
    user.setActivationCode(UUIDGenerator.generatedUI());
    userRepository.save(user);

    if(userRepository.findUsersByEmail(user.getEmail()).isPresent()){
      String message = String.format(
              "Hello, %s! \n" +
                      "Welcome to Rent-platform. Please," +
                      " visit next link: http://localhost:8080/api/registration/activate/%s \n" +
                      "for activation",
              user.getUsername(),
              user.getActivationCode()
      );
    mailSenderService.send(user.getEmail(),"Activation link",message);

    }

    return userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
  }

  @Transactional
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

  @Transactional
  @Override
  public Page<User> search(Pageable page) {
    return userRepository.findAll(page);
  }

  @Override
  public Optional<User> findByLogin(String login) {
    return userRepository.findByUserLogin(login);
  }

  @Override
  public boolean activateUser(String code) {
    Optional<User> user = userRepository.findUsersByActivationCode(code);
    if(user.isPresent() && user.get().getStatus().equals(Status.NOT_ACTIVE)){
      user.get().setStatus(Status.ACTIVE);
      userRepository.save(user.get());
      return true;
    }
    return false;
  }

  public void addRole(User user,Role role){
    if (user.getRoles() != null) {
      user.getRoles().add(role);
      role.getUsers().add(user);
    } else {
      Set<Role> rolesList = new HashSet<>();
      rolesList.add(role);
      user.setRoles(rolesList);
      role.getUsers().add(user);
     }
  }
}
