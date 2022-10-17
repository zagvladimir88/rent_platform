package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Location;
import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.LocationService;
import com.zagvladimir.service.UserService;
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

    Role role1 = roleRepository.findRoleByName("ROLE_USER");
    addRole(user,role1);
    Role role2 = roleRepository.findRoleByName("ROLE_USER");
    addRole(user,role2);

    user.setRegistrationDate(new Timestamp(new Date().getTime()));
    user.setCreationDate(user.getRegistrationDate());
    user.setModificationDate(user.getRegistrationDate());
    Location location = locationService.findById(locationId).orElse(null);
    user.setLocation(location);
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

    return userRepository.save(user);
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
