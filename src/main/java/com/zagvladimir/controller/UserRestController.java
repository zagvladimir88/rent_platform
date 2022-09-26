package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserSearchRequest;
import com.zagvladimir.domain.User;

import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


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
        model.put("users",users);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {
        long userId = Long.parseLong(id);
        return new ResponseEntity<>(Collections.singletonMap("user", userService.findById(userId)), HttpStatus.OK);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<Map<String, Object>> findByLogin(@PathVariable String login) {
        return new ResponseEntity<>(Collections.singletonMap("user", userService.findByLogin(login)), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest createRequest) {

        User newUser = new User();
        newUser.setUsername(createRequest.getUsername());
        newUser.setUserPassword(passwordEncoder.encode(createRequest.getUserPassword()));
        newUser.setUserLogin(createRequest.getUserLogin());
        newUser.setLocationId(createRequest.getLocationId());
        newUser.setLocationDetails(createRequest.getLocationDetails());
        newUser.setPhoneNumber(createRequest.getPhoneNumber());
        newUser.setMobileNumber(createRequest.getMobileNumber());
        newUser.setEmail(createRequest.getEmail());
        newUser.setRegistrationDate(new Timestamp(new Date().getTime()));
        newUser.setCreationDate(new Timestamp(new Date().getTime()));
        newUser.setModificationDate(new Timestamp(new Date().getTime()));
        newUser.setStatus(createRequest.getStatus());

        userService.create(newUser);

        userService.createRoleRow(newUser.getId(), roleRepository.findRoleByName("ROLE_USER").getId());


        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("user", newUser.getUsername());
        model.put("users", users);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsersById(@PathVariable Long id){

        userService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


}
