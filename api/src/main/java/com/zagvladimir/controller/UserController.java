package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.UserMapper;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.controller.response.UserResponse;
import com.zagvladimir.domain.User;
import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.service.user.UserService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Tag(name = "User controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

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
  @GetMapping
  public ResponseEntity<Object> findAllUsers(@ParameterObject Pageable pageable) {
    Page<UserResponse> users = userService.findAll(pageable).map(userMapper::toResponse);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @Operation(
      summary = "Gets user by ID",
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
    UserResponse user = userMapper.toResponse(userService.findById(id));
    return new ResponseEntity<>(Collections.singletonMap("user", user), HttpStatus.OK);
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
    UserResponse userResponse = userMapper.toResponse(userService.findByLogin(login));
    return new ResponseEntity<>(Collections.singletonMap("user", userResponse), HttpStatus.OK);
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
    userService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("The user was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Update the User",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User update successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "User not updated, Illegal Arguments",
            content = @Content)
      })
  @PutMapping(value = "/{id}")
  @Transactional
  public ResponseEntity<Object> updateUser(
      @PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
    User updatedUser = userMapper.convertUpdateRequest(userUpdateRequest, userService.findById(id));
    UserResponse userResponse = userMapper.toResponse(userService.update(updatedUser));
    return new ResponseEntity<>(Collections.singletonMap("user", userResponse), HttpStatus.OK);
  }
}
