package edu.harvard.iq.dataverse.marketplace.controller.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import edu.harvard.iq.dataverse.marketplace.model.Role;
import edu.harvard.iq.dataverse.marketplace.model.User;
import edu.harvard.iq.dataverse.marketplace.payload.JwtResponse;
import edu.harvard.iq.dataverse.marketplace.payload.LoginRequest;
import edu.harvard.iq.dataverse.marketplace.payload.MessageResponse;
import edu.harvard.iq.dataverse.marketplace.payload.SignupRequest;
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

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
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    
    System.out.println("signup request: " + signUpRequest);
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    
    Set<Role> roles = new HashSet<>();

    /**
     * Role logic removed
     */

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
