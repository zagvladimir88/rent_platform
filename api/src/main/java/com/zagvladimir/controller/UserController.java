package com.zagvladimir.controller;


import com.zagvladimir.dto.requests.users.UserChangeAddressRequest;
import com.zagvladimir.dto.requests.users.UserChangeCredentialsRequest;
import com.zagvladimir.dto.requests.users.UserUpdateRequest;
import com.zagvladimir.dto.response.user.UserResponse;
import com.zagvladimir.exception.ErrorContainer;
import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

  @Operation(summary = "Gets all users with pagination")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the users", content =
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))})
  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
  @PreAuthorize(value = "hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Object> findAllUsers(@ParameterObject Pageable pageable) {
    return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
  }

  @Operation(summary = "Gets user by ID")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the user by id", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))})})
  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
  @GetMapping("/{id}")
  @PreAuthorize(
      "@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {
    Long userId = Long.parseLong(id);
    return new ResponseEntity<>(Collections.singletonMap("user", userService.findById(userId)), HttpStatus.OK);
  }

  @Operation(summary = "Gets user by Login")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the user by login", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))}),
        @ApiResponse(responseCode = "404", description = "User not found", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorContainer.class)))})})
  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
  @PreAuthorize(value = "hasRole('ADMIN')")
  @GetMapping("/login/{login}")
  public ResponseEntity<Map<String, Object>> findByLogin(@PathVariable String login) {
    return new ResponseEntity<>(Collections.singletonMap("user", userService.findByLogin(login)), HttpStatus.OK);
  }

  @Operation(summary = "Soft delete user")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status changed to deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
  @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  @PatchMapping("/delete/{id}")
  public ResponseEntity<Object> softDeleteUsersById(@PathVariable Long id) {
    userService.softDelete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("The user was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(summary = "Update the User")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User update successfully", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "500", description = "User not updated, Illegal Arguments", content = @Content)})
  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
  @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
  @PutMapping(value = "/{id}")
  @Transactional
  public ResponseEntity<Object> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
    return new ResponseEntity<>(Collections.singletonMap("user", userService.update(request,id)), HttpStatus.OK);
  }

    @Operation(summary = "Change user credentials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "credentials update successfully", content =
           @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "500", description = "User not updated, Illegal Arguments", content = @Content)})
    @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
    @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/change-credential")
    @Transactional
    public ResponseEntity<Object> userChangeCredentials(
            @PathVariable Long id, @Valid @RequestBody UserChangeCredentialsRequest request) {
        return new ResponseEntity<>(Collections.singletonMap("user", userService.update(request,id)), HttpStatus.OK);
    }

    @Operation(summary = "Change user address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update successfully", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "500", description = "User not updated, Illegal Arguments", content = @Content)})
    @Parameter(in = ParameterIn.HEADER,  name = "X-Auth-Token", required = true, description = "JWT Token, can be generated in auth controller /auth")
    @PreAuthorize("@userServiceImpl.findById(#id).credentials.userLogin.equals(principal.username) or hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/change-address")
    @Transactional
    public ResponseEntity<Object> userChangeAddress(
            @PathVariable Long id, @Valid @RequestBody UserChangeAddressRequest request) {
        return new ResponseEntity<>(Collections.singletonMap("user", userService.update(request,id)), HttpStatus.OK);
    }
}
