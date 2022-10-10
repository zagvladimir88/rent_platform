package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.LocationService;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;
  private final RoleRepository roleRepository;
  private final LocationService locationService;
  private final BCryptPasswordEncoder passwordEncoder;

  @Operation(summary = "Gets all users")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the users",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = User.class)))
            })
      })
  @GetMapping
  public ResponseEntity<Object> findAllUsers() {
    return new ResponseEntity<>(
        Collections.singletonMap("result", userService.findAll()), HttpStatus.OK);
  }

  @Operation(
      summary = "Gets all users with pagination",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the users",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = User.class))))
      })
  @GetMapping("/search")
  public ResponseEntity<Object> findAllUsersWithParams(@ParameterObject Pageable pageable) {
    Page<User> users = userService.findAll(pageable);

    Map<String, Object> model = new HashMap<>();
    model.put("users", users);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }

  @Operation(summary = "Gets user by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Found the user by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = User.class)))
            })
      })
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {
    long userId = Long.parseLong(id);
    return new ResponseEntity<>(
        Collections.singletonMap("user", userService.findById(userId)), HttpStatus.OK);
  }


  @Operation(summary = "Gets user by Login")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the user by login",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = User.class)))
                          })
          })
  @GetMapping("/login/{login}")
  public ResponseEntity<Map<String, Object>> findByLogin(@PathVariable String login) {
    return new ResponseEntity<>(
        Collections.singletonMap("user", userService.findByLogin(login)), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new User",
      responses = {
        @ApiResponse( responseCode = "201", description = "User create successfully",content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse( responseCode = "409", description = "User not created, Conflict", content = @Content),
        @ApiResponse( responseCode = "500", description = "User not created, Illegal Arguments", content = @Content)
      })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest createRequest) {

    User newUser = new User();
    newUser.setUsername(createRequest.getUsername());
    newUser.setUserPassword(passwordEncoder.encode(createRequest.getUserPassword()));
    newUser.setUserLogin(createRequest.getUserLogin());
    newUser.setLocation(locationService.findById(createRequest.getLocationId()).get());
    newUser.setLocationDetails(createRequest.getLocationDetails());
    newUser.setPhoneNumber(createRequest.getPhoneNumber());
    newUser.setMobileNumber(createRequest.getMobileNumber());
    newUser.setEmail(createRequest.getEmail());
    newUser.setRegistrationDate(new Timestamp(new Date().getTime()));
    newUser.setCreationDate(new Timestamp(new Date().getTime()));
    newUser.setModificationDate(new Timestamp(new Date().getTime()));
    newUser.setStatus(createRequest.getStatus());

    userService.create(newUser);

    userService.createRoleRow(newUser.getId(), roleRepository.findRoleByName("ROLE_USER").getId());

    Map<String, Object> model = new HashMap<>();
    model.put("user", userService.findById(newUser.getId()));

    return new ResponseEntity<>(model, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Delete user",
      description = "This can only be done by the logged in user.",
      responses = {
        @ApiResponse(responseCode = "200", description = "user deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUsersById(@PathVariable Long id) {

    userService.delete(id);
    Map<String, Object> model = new HashMap<>();
    model.put("id", id);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }


  @Operation(
          summary = "Update the User",
          responses = {
                  @ApiResponse( responseCode = "200", description = "User update successfully",content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                  @ApiResponse( responseCode = "500", description = "User not updated, Illegal Arguments", content = @Content)
          })
  @PutMapping(value = "/{id}")
  @Transactional
  public ResponseEntity<Object> updateUser(
      @PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
    User updatedUser = userService.findById(id);

    updatedUser.setUsername(userUpdateRequest.getUsername());
    updatedUser.setUserPassword(passwordEncoder.encode(userUpdateRequest.getUserPassword()));
    updatedUser.setUserLogin(userUpdateRequest.getUserLogin());
    updatedUser.setLocation(locationService.findById(userUpdateRequest.getLocationId()).get());
    updatedUser.setLocationDetails(userUpdateRequest.getLocationDetails());
    updatedUser.setPhoneNumber(userUpdateRequest.getPhoneNumber());
    updatedUser.setMobileNumber(userUpdateRequest.getMobileNumber());
    updatedUser.setEmail(userUpdateRequest.getEmail());
    updatedUser.setModificationDate(new Timestamp(new Date().getTime()));
    updatedUser.setStatus(userUpdateRequest.getStatus());

    userService.create(updatedUser);

    Map<String, Object> model = new HashMap<>();
    model.put("user", userService.findById(updatedUser.getId()));

    return new ResponseEntity<>(model, HttpStatus.OK);
  }
}
