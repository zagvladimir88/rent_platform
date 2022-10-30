package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.UserMapper;
import com.zagvladimir.controller.requests.users.UserChangeAddressRequest;
import com.zagvladimir.controller.requests.users.UserChangeCredentialsRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.controller.response.user.UserResponse;
import com.zagvladimir.domain.user.User;
import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasRole('ADMIN')")
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
      },
          parameters = {
                  @Parameter(
                          in = ParameterIn.HEADER,
                          name = "X-Auth-Token",
                          required = true,
                          description = "JWT Token, can be generated in auth controller /auth")
          })
  @GetMapping("/{id}")
  @PreAuthorize(
      "@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {
    Long userId = Long.parseLong(id);
    UserResponse user = userMapper.toResponse(userService.findById(userId));
    return new ResponseEntity<>(Collections.singletonMap("user", user), HttpStatus.OK);
  }

  @Operation(
      summary = "Gets user by Login",
      responses = {
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
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @GetMapping("/login/{login}")
  public ResponseEntity<Map<String, Object>> findByLogin(@PathVariable String login) {
    UserResponse userResponse = userMapper.toResponse(userService.findByLogin(login));
    return new ResponseEntity<>(Collections.singletonMap("user", userResponse), HttpStatus.OK);
  }

  @Operation(
      summary = "Soft delete user",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Status changed to deleted",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  @PatchMapping("/delete/{id}")
  public ResponseEntity<Object> softDeleteUsersById(@PathVariable Long id) {
    userService.softDelete(id);
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
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  @PutMapping(value = "/{id}")
  @Transactional
  public ResponseEntity<Object> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
    User updatedUser = userMapper.convertUpdateRequest(request, userService.findById(id));
    UserResponse userResponse = userMapper.toResponse(userService.update(updatedUser));
    return new ResponseEntity<>(Collections.singletonMap("user", userResponse), HttpStatus.OK);
  }

    @Operation(
            summary = "Change user credentials",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "credentials update successfully",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "User not updated, Illegal Arguments",
                            content = @Content)
            },
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                            required = true, description = "JWT Token, can be generated in auth controller /auth")
            })
    @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/change-credential")
    @Transactional
    public ResponseEntity<Object> userChangeCredentials(
            @PathVariable Long id, @Valid @RequestBody UserChangeCredentialsRequest request) {
        User updatedUser = userMapper.convertUpdateRequest(request, userService.findById(id));
        UserResponse userResponse = userMapper.toResponse(userService.update(updatedUser));
        return new ResponseEntity<>(Collections.singletonMap("user", userResponse), HttpStatus.OK);
    }

    @Operation(
            summary = "Change user address",
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
            },
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,  name = "X-Auth-Token",
                            required = true, description = "JWT Token, can be generated in auth controller /auth")
            })
    @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/change-address")
    @Transactional
    public ResponseEntity<Object> userChangeAddress(
            @PathVariable Long id, @Valid @RequestBody UserChangeAddressRequest request) {
        User updatedUser = userMapper.convertUpdateRequest(request, userService.findById(id));
        UserResponse userResponse = userMapper.toResponse(userService.update(updatedUser));
        return new ResponseEntity<>(Collections.singletonMap("user", userResponse), HttpStatus.OK);
    }
}
