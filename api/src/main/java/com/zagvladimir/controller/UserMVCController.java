package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserSearchRequest;
import com.zagvladimir.domain.User;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserMVCController {

    private final UserService userService;

    @GetMapping("/users")
    public ModelAndView findAllUsers() {
        List<User> users = userService.findAll();

        ModelAndView model = new ModelAndView();
        model.addObject("user", "Slava");
        model.addObject("users", users);

        model.setViewName("users");

        return model;
    }

    @GetMapping("/users/search")
    public ModelAndView findAllUsersWithParams(@ModelAttribute UserSearchRequest userSearchRequest) {

        int verifiedLimit = Integer.parseInt(userSearchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(userSearchRequest.getOffset());

        List<User> users = userService.search(verifiedLimit, verifiedOffset);

        ModelAndView model = new ModelAndView();
        model.addObject("user", "Slava");
        model.addObject("users", users);

        model.setViewName("users");

        return model;
    }

    @GetMapping("/users/{id}")
    public ModelAndView findUserById(@PathVariable String id) {

        //We have added id parsing and number format checking
        long userId = Long.parseLong(id);
        User user = userService.findById(userId);

        ModelAndView model = new ModelAndView();
        model.addObject("userName", user.getUsername());
        model.addObject("user", user);

        model.setViewName("user");

        return model;
    }

    @PostMapping
    //Jackson
    public ModelAndView createUser(@RequestBody UserCreateRequest createRequest) {

        User user = new User();
        user.setUsername(createRequest.getUsername());
        user.setUserPassword(createRequest.getUserPassword());
        user.setLocationId(createRequest.getLocationId());
        user.setLocationDetails(createRequest.getLocationDetails());
        user.setPhoneNumber(createRequest.getPhoneNumber());
        user.setMobileNumber(createRequest.getMobileNumber());
        user.setEmail(createRequest.getEmail());
        user.setRegistrationDate(new Timestamp(new Date().getTime()));
        user.setCreationDate(new Timestamp(new Date().getTime()));
        user.setModificationDate(new Timestamp(new Date().getTime()));

        userService.create(user);

        List<User> users = userService.findAll();

        ModelAndView model = new ModelAndView();
        model.addObject("user", "Slava");
        model.addObject("users", users);

        model.setViewName("users");

        return model;
    }
}
