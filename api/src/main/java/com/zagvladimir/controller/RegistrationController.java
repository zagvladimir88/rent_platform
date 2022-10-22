package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.UserMapper;
import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.response.UserResponse;
import com.zagvladimir.domain.User;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Tag(name = "Registration controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @Operation(
            summary = "Registered a new User",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User not registered, Conflict",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "User not registered, Illegal Arguments",
                            content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserCreateRequest createRequest) {
        User newUser = userMapper.userCreateRequestToUser(createRequest);
        userService.create(newUser, createRequest.getLocationId());
        return new ResponseEntity<>(
                userMapper.userToUserResponse(userService.findById(newUser.getId())), HttpStatus.CREATED);
    }

//    @GetMapping("/activate/{code}")
//    public String activate(Model model, @PathVariable String code) {
//        boolean isActivated = userSevice.activateUser(code);
//
//        if (isActivated) {
//            model.addAttribute("message", "User successfully activated");
//        } else {
//            model.addAttribute("message", "Activation code is not found!");
//        }
//
//        return "login";
//    }

}
