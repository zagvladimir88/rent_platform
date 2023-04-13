package com.zagvladimir.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.zagvladimir.domain.RefreshToken;
import com.zagvladimir.dto.requests.auth.LoginRequest;
import com.zagvladimir.dto.requests.auth.SignupRequest;
import com.zagvladimir.dto.requests.auth.TokenRefreshRequest;
import com.zagvladimir.dto.response.auth.JwtResponse;
import com.zagvladimir.dto.response.auth.MessageResponse;
import com.zagvladimir.dto.response.auth.TokenRefreshResponse;
import com.zagvladimir.dto.response.user.UserResponse;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.security.exception.TokenRefreshException;
import com.zagvladimir.security.jwt.JwtUtils;
import com.zagvladimir.security.services.RefreshTokenService;
import com.zagvladimir.security.services.UserDetailsImpl;
import com.zagvladimir.service.role.RoleService;
import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @Operation(summary = "SignUp a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "409", description = "User not registered, Conflict", content = @Content),
            @ApiResponse(responseCode = "500", description = "User not registered, Illegal Arguments", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
        if (userRepository.existsUserByCredentialsUserLogin(signUpRequest.getUserLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsUserByCredentialsEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.create(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getCredentials().getUserLogin());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
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
