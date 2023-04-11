package com.zagvladimir.service.user;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.user.User;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.dto.requests.users.UserChangeAddressRequest;
import com.zagvladimir.dto.requests.users.UserChangeCredentialsRequest;
import com.zagvladimir.dto.requests.users.UserCreateRequest;
import com.zagvladimir.dto.requests.users.UserUpdateRequest;
import com.zagvladimir.dto.response.user.UserResponse;
import com.zagvladimir.mappers.UserMapper;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.mail.MailSenderService;
import com.zagvladimir.service.pdf.PDFService;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String ACTIVATION_URL =
      "http://localhost:8080/api/registration/activate/%s/";
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final MailSenderService mailSenderService;
  private final PDFService pdfService;
  private final ItemLeasedRepository itemLeasedRepository;
  private final UserMapper userMapper;

  @Override
  public List<UserResponse> findAll() {
    return userRepository.findAll().stream().map(userMapper::toResponse).collect(Collectors.toList());
  }

  @Override
  public Page<UserResponse> findAll(Pageable page) {
    return userRepository.findAll(page).map(userMapper::toResponse);
  }

  @Transactional
  @Override
  public UserResponse create(UserCreateRequest request) throws MessagingException {
    User user = userMapper.convertCreateRequest(request);
    addRole(user, roleRepository.findRoleByName("ROLE_USER"));
    user.setRegistrationDate(new Timestamp(new Date().getTime()));
    user.getCredentials()
        .setUserPassword(passwordEncoder.encode(user.getCredentials().getUserPassword()));

    user.setStatus(Status.NOT_ACTIVE);
    user.setActivationCode(UUIDGenerator.generatedUI());
    userRepository.save(user);

    if (userRepository.findUsersByCredentials_Email(user.getCredentials().getEmail()).isPresent()) {
      sendEmail(user);
    }
    return userRepository.findById(user.getId()).map(userMapper::toResponse).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public UserResponse findById(Long userId) {
    return userRepository
        .findById(userId).map(userMapper::toResponse)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The user with id:%d not found", userId)));
  }

  @Transactional
  @Override
  public UserResponse update(UserUpdateRequest request,Long id) {
    Optional<User> user = userRepository.findById(id);
    User updatedUser = null;
    if(user.isPresent()) {
      updatedUser = userMapper.convertUpdateRequest(request, user.get());
    } else throw new EntityNotFoundException("User with ID: " + id + " not found");
    updatedUser.setModificationDate(new Timestamp(new Date().getTime()));
    updatedUser
        .getCredentials()
        .setUserPassword(passwordEncoder.encode(updatedUser.getCredentials().getUserPassword()));
    userRepository.save(updatedUser);
    return userRepository.findById(updatedUser.getId()).map(userMapper::toResponse).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  @Override
  public UserResponse update(UserChangeCredentialsRequest request, Long id) {
    Optional<User> user = userRepository.findById(id);
    User updatedUser = null;
    if(user.isPresent()) {
      updatedUser = userMapper.convertUpdateRequest(request, user.get());
    } else throw new EntityNotFoundException("User with ID: " + id + " not found");
    updatedUser.setModificationDate(new Timestamp(new Date().getTime()));
    updatedUser
            .getCredentials()
            .setUserPassword(passwordEncoder.encode(updatedUser.getCredentials().getUserPassword()));
    userRepository.save(updatedUser);
    return userRepository.findById(updatedUser.getId()).map(userMapper::toResponse).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  @Override
  public UserResponse update(UserChangeAddressRequest request, Long id) {
    Optional<User> user = userRepository.findById(id);
    User updatedUser = null;
    if(user.isPresent()) {
      updatedUser = userMapper.convertUpdateRequest(request, user.get());
    } else throw new EntityNotFoundException("User with ID: " + id + " not found");
    updatedUser.setModificationDate(new Timestamp(new Date().getTime()));
    updatedUser
            .getCredentials()
            .setUserPassword(passwordEncoder.encode(updatedUser.getCredentials().getUserPassword()));
    userRepository.save(updatedUser);
    return userRepository.findById(updatedUser.getId()).map(userMapper::toResponse).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  @Override
  public Long delete(Long id) {
    userRepository.deleteById(id);
    return id;
  }

  @Override
  public UserResponse findByLogin(String login) {
    return userRepository
        .findByCredentialsUserLogin(login)
            .map(userMapper::toResponse)
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

  @Override
  public void confirmItemBooking(Long userID) throws MessagingException {
    User user =
        userRepository
            .findById(userID)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("The User with id: %d not found", userID)));

    Set<ItemLeased> itemsLeasedSet = user.getItemsLeased();
    String pathToBilling = pdfService.generateAnInvoice(itemsLeasedSet, user.getId());

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("recipientName", user.getFirstName());
    templateModel.put("email", user.getCredentials().getEmail());
    templateModel.put("name", user.getFirstName());
    templateModel.put("item_name", "item name");

    mailSenderService.sendConfirmBookingMail(
        user.getCredentials().getEmail(), "Confirm Booking", pathToBilling, templateModel);

    for (ItemLeased leased : itemsLeasedSet) {
      leased.setStatus(Status.ACTIVE);
      itemLeasedRepository.save(leased);
    }
  }

  @Override
  public Long softDelete(Long userId) {
    User toUpdate =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("The user with id: %d not found", userId)));
    toUpdate.setStatus(Status.DELETED);
    userRepository.save(toUpdate);
    return userId;
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
