package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserSearchRequest;
import com.zagvladimir.domain.Status;
import com.zagvladimir.domain.User;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> findAllUsers() {
        return new ResponseEntity<>(Collections.singletonMap("result", userService.findAll()),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAllUsersWithParams(@ModelAttribute UserSearchRequest userSearchRequest) {

        int verifiedLimit = Integer.parseInt(userSearchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(userSearchRequest.getOffset());

        List<User> users = userService.search(verifiedLimit, verifiedOffset);

        Map<String, Object> model = new HashMap<>();
        model.put("user", "Slava");
        model.put("users", users);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {

        //We have added id parsing and number format checking
        long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("user", userService.findById(userId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest createRequest) {

        User user = new User();
        user.setUsername(createRequest.getUsername());
        user.setUser_password(createRequest.getUser_password());
        user.setUser_login(createRequest.getUser_login());
        user.setLocation_id(createRequest.getLocation_id());
        user.setLocation_details(createRequest.getLocation_details());
        user.setPhone_number(createRequest.getPhone_number());
        user.setMobile_number(createRequest.getMobile_number());
        user.setEmail(createRequest.getEmail());
        user.setRegistration_date(new Timestamp(new Date().getTime()));
        user.setCreation_date(new Timestamp(new Date().getTime()));
        user.setModification_date(new Timestamp(new Date().getTime()));
        user.setStatus(createRequest.getStatus());

        userService.create(user);

        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("user", user.getUsername());
        model.put("users", users);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUsersById(@PathVariable Long id){

        userService.delete(id);

        List<User> users = userService.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
