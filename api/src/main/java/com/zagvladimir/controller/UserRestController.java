package com.zagvladimir.controller;



import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.controller.mappers.UserMapper;
import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.controller.response.UserResponse;
import com.zagvladimir.domain.User;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;
@Slf4j
@Tag(name = "User controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;
  private final UserMapper userMapper;

  @Operation(summary = "Gets all users",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Found the users",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
                          })
          })
  @GetMapping
  public ResponseEntity<Object> findAllUsers() {
    return new ResponseEntity<>(Collections.singletonMap("result", userService.findAll().stream().map(userMapper::userToUserResponse)), HttpStatus.OK);
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
                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
      })
  @GetMapping("/search")
  public ResponseEntity<Object> findAllUsersWithParams(@ParameterObject Pageable pageable) {
    return new ResponseEntity<>(userService.findAll(pageable).map(userMapper::userToUserResponse), HttpStatus.OK);
  }

  @Operation(summary = "Gets user by ID",
        responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user by id",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
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
                                          array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
                          }),
                  @ApiResponse(
                          responseCode = "404",
                          description = "User not found",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          array = @ArraySchema(schema = @Schema(implementation = ErrorContainer.class)))
                          })
          })
  @GetMapping("/login/{login}")
  public ResponseEntity<Map<String, Object>> findByLogin(@PathVariable String login) {
    return new ResponseEntity<>(Collections.singletonMap("user", userMapper.userToUserResponse(userService.findByLogin(login).orElseThrow(EntityNotFoundException::new))), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new User",
      responses = {
        @ApiResponse( responseCode = "201", description = "User create successfully",content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse( responseCode = "409", description = "User not created, Conflict", content = @Content),
        @ApiResponse( responseCode = "500", description = "User not created, Illegal Arguments", content = @Content)
      })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest createRequest) {
    User newUser = userMapper.userCreateRequestToUser(createRequest);
    userService.create(newUser, createRequest.getLocationId());
    return new ResponseEntity<>(userMapper.userToUserResponse(userService.findById(newUser.getId())), HttpStatus.CREATED);
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
                  @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                  @ApiResponse( responseCode = "500", description = "User not updated, Illegal Arguments", content = @Content)
          })
  @PutMapping(value = "/{id}")
  @Transactional
  public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
    User updatedUser = userMapper.updateUserFromUpdateRequest(userUpdateRequest,userService.findById(id));
    userService.update(updatedUser);
    return new ResponseEntity<>(userMapper.userToUserResponse(userService.findById(updatedUser.getId())), HttpStatus.OK);
  }
}
