package com.zagvladimir.controller;

import com.zagvladimir.dto.requests.users.UserCreateRequest;
import com.zagvladimir.dto.response.user.UserResponse;
import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Registration controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

  @Autowired private UserService userService;

  @Operation(summary = "Registered a new User")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "409", description = "User not registered, Conflict", content = @Content),
        @ApiResponse(responseCode = "500", description = "User not registered, Illegal Arguments", content = @Content)
      })
  @PostMapping
  @Transactional
  public ResponseEntity<Object> addUser(@Valid @RequestBody UserCreateRequest createRequest)
      throws MessagingException {
    return new ResponseEntity<>(userService.create(createRequest), HttpStatus.CREATED);
  }

  @Operation(summary = "Activate a User")
  @GetMapping("/activate/{code}")
  public ResponseEntity<Object> activate(@PathVariable String code) {
    boolean isActivated = userService.activateUser(code);
    Map<String, String> response = null;
    if (isActivated) {
      response = Collections.singletonMap("message", "User successfully activated");
    } else {
      response = Collections.singletonMap("message", "User not found or this link is outdated");
    }
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
