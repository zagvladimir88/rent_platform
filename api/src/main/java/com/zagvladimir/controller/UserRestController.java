package com.zagvladimir.controller;





import com.zagvladimir.controller.requests.SearchRequest;
import com.zagvladimir.controller.requests.users.UserCreateRequest;
import com.zagvladimir.controller.requests.users.UserUpdateRequest;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.service.LocationService;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Tag(name = "User", description = "The Rent-platform API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final LocationService locationService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Operation(summary = "Gets all users", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    })
    })
    @GetMapping
    public ResponseEntity<Object> findAllUsers() {
        return new ResponseEntity<>(Collections.singletonMap("result", userService.findAll()),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAllUsersWithParams(@ModelAttribute SearchRequest searchRequest) {

        int verifiedLimit = Integer.parseInt(searchRequest.getPage());
        int verifiedOffset = Integer.parseInt(searchRequest.getSize());
        Pageable page = PageRequest.of(verifiedLimit, verifiedOffset, Sort.by("id").ascending());

        Page<User> users = userService.search(page);

        Map<String, Object> model = new HashMap<>();

        model.put("users",users);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


    @Operation(summary = "Gets user by ID", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Found the user by id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    })
    })
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
        newUser.setLocation(locationService.findById(createRequest.getLocationId()).get());
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


        Map<String, Object> model = new HashMap<>();
        model.put("user", userService.findById(newUser.getId()));

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsersById(@PathVariable Long id){

        userService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody  UserUpdateRequest userUpdateRequest){
        User updatedUser = userService.findById(id);

        updatedUser.setUsername(userUpdateRequest.getUsername());
        updatedUser.setUserPassword(passwordEncoder.encode(userUpdateRequest.getUserPassword()));
        updatedUser.setUserLogin(userUpdateRequest.getUserLogin());
        updatedUser.setLocation(locationService.findById(userUpdateRequest.getLocationId()).get());
        updatedUser.setLocationDetails(userUpdateRequest.getLocationDetails());
        updatedUser.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        updatedUser.setMobileNumber(userUpdateRequest.getMobileNumber());
        updatedUser.setEmail(userUpdateRequest.getEmail());
        updatedUser.setModificationDate(new Timestamp(new Date().getTime()));
        updatedUser.setStatus(userUpdateRequest.getStatus());

        userService.create(updatedUser);

        Map<String, Object> model = new HashMap<>();
        model.put("user", userService.findById(updatedUser.getId()));

        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
