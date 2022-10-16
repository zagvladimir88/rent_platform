package com.zagvladimir.controller;



import com.zagvladimir.controller.mappers.UserMapper;
import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "User controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Operation(summary = "Gets all users",
          responses = {
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
    return new ResponseEntity<>(Collections.singletonMap("result", userService.findAll()), HttpStatus.OK);
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
    return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
  }

  @Operation(summary = "Gets user by ID",
        responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = User.class)))
            })
      })
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findUserById(@PathVariable Long id) {

    return new ResponseEntity<>(Collections.singletonMap("user",userMapper.userToUserResponse(userService.findById(id))), HttpStatus.OK);
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
                          }),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Found the user by login",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = User.class)))
                          })
          })
  @GetMapping("/login/{login}")
  public ResponseEntity<Map<String, Object>> findByLogin(@PathVariable String login) {
    return new ResponseEntity<>(Collections.singletonMap("user", userService.findByLogin(login)), HttpStatus.OK);
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

    User newUser = userMapper.userCreateRequestToUser(createRequest);
    Role role = roleRepository.findRoleByName("ROLE_USER");
    userService.create(newUser,role,createRequest.getLocationId());
    return new ResponseEntity<>(userService.findById(newUser.getId()), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Delete user",
      responses = {
        @ApiResponse(responseCode = "200", description = "user was deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUsersById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
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
  public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
    User updatedUser = userMapper.updateUserFromUpdateRequest(userUpdateRequest,userService.findById(id));
    userService.update(updatedUser);
    return new ResponseEntity<>(userService.findById(updatedUser.getId()), HttpStatus.OK);
  }


}
