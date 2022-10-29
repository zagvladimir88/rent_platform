package com.zagvladimir.controller;

import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Admin controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @PatchMapping("/confirm/{clientId}")
    public ResponseEntity<Map<String, Object>> confirmItemBooking(@PathVariable("clientId") String clientId)
            throws MessagingException {
        Long userID = Long.parseLong(clientId);
        userService.confirmItemBooking(userID);
        return new ResponseEntity<>(
                Collections.singletonMap("user", "Confirm successfully"), HttpStatus.OK);
    }

}
