package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserSearchRequest;
import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.dto.RoleDTO;
import com.zagvladimir.dto.UserDTO;
import com.zagvladimir.mappers.UserListMapper;
import com.zagvladimir.mappers.UserMapper;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserMapper userMapper;
    private final UserListMapper userListMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> findAllUsers() {
        List<User> userList = userService.findAll();
        return new ResponseEntity<>(Collections.singletonMap("result", userListMapper.toDTOs(userList)),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAllUsersWithParams(@ModelAttribute UserSearchRequest userSearchRequest) {

        int verifiedLimit = Integer.parseInt(userSearchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(userSearchRequest.getOffset());

        List<User> users = userService.search(verifiedLimit, verifiedOffset);

        Map<String, Object> model = new HashMap<>();
        model.put("user", "Slava");
        model.put("users",userListMapper.toDTOs(users));

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        User user = userService.findById(userId);
        return new ResponseEntity<>(Collections.singletonMap("user", userMapper.toDTO(user)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest createRequest) {

        User user = new User();
        user.setUsername(createRequest.getUsername());
        user.setUserPassword(createRequest.getUserPassword());
        user.setUserLogin(createRequest.getUserLogin());
        user.setLocationId(createRequest.getLocationId());
        user.setLocationDetails(createRequest.getLocationDetails());
        user.setPhoneNumber(createRequest.getPhoneNumber());
        user.setMobileNumber(createRequest.getMobileNumber());
        user.setEmail(createRequest.getEmail());
        user.setRegistrationDate(new Timestamp(new Date().getTime()));
        user.setCreationDate(new Timestamp(new Date().getTime()));
        user.setModificationDate(new Timestamp(new Date().getTime()));
        user.setStatus(createRequest.getStatus());

        userService.create(user);

        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("user", user.getUsername());
        model.put("users", users);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsersById(@PathVariable Long id){

        long deleteId = userService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


}
