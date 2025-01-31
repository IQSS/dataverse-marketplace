package edu.harvard.iq.dataverse.marketplace.controller.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.iq.dataverse.marketplace.model.User;
import edu.harvard.iq.dataverse.marketplace.openapi.annotations.AuthAPIDocs;
import edu.harvard.iq.dataverse.marketplace.payload.ServerMessageResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.JwtResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.LoginRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.SignupRequest;
import edu.harvard.iq.dataverse.marketplace.repository.RoleRepo;
import edu.harvard.iq.dataverse.marketplace.repository.UserRepo;
import edu.harvard.iq.dataverse.marketplace.security.UserDetailsImpl;
import edu.harvard.iq.dataverse.marketplace.security.jwt.JwtUtils;

/**
 * This class is used to authenticate users and register new users.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @AuthAPIDocs.Login
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('ADMIN')")
    @AuthAPIDocs.Signup
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        ServerMessageResponse messageResponse = null;

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            messageResponse = new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                    "Username is already taken.",
                    "Error during the creation of the user, a user with this username already exist.");

            return ResponseEntity.badRequest().body(messageResponse);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            messageResponse = new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                    "Email is already taken.",
                    "Error during the creation of the user, a user with this email already exist.");
            return ResponseEntity.badRequest().body(messageResponse);
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        messageResponse = new ServerMessageResponse(HttpStatus.OK,
                "User registered successfully.",
                "The user was successfully registered.");

        return ResponseEntity.ok(messageResponse);
    }
}
