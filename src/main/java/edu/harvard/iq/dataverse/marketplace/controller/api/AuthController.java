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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.harvard.iq.dataverse.marketplace.model.Role;
import edu.harvard.iq.dataverse.marketplace.model.User;
import edu.harvard.iq.dataverse.marketplace.openapi.annotations.AuthAPIDocs;
import edu.harvard.iq.dataverse.marketplace.payload.ServerMessageResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.RoleDTO;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.LoginRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.RoleCreationRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.SignupRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.response.JwtResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.response.RoleCreationResponse;
import edu.harvard.iq.dataverse.marketplace.repository.RoleRepo;
import edu.harvard.iq.dataverse.marketplace.repository.UserRepo;
import edu.harvard.iq.dataverse.marketplace.security.UserDetailsImpl;
import edu.harvard.iq.dataverse.marketplace.security.jwt.JwtUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

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

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @AuthAPIDocs.RoleCreationRequest
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreationRequest roleCreationRequest) {
        
        if(roleRepository.existsByName(roleCreationRequest.getRoleName().toUpperCase())) {
            return ResponseEntity.badRequest().body(new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                    "Role already exists.",
                    "Error during the creation of the role, a role with this name already exist."));
        }
        
        Role role = new Role();
        role.setName(roleCreationRequest.getRoleName().toUpperCase());
        roleRepository.save(role);
        return ResponseEntity.ok(new RoleCreationResponse(role));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/role/{roleId}/user/{userId}")
    @AuthAPIDocs.AssignRole
    public ResponseEntity<?> assignRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Long userId) {
        
        try {

            Role role = roleRepository.findById(roleId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);

            if(user.getRoles().contains(role)) {
                return ResponseEntity.badRequest().body(
                    new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                        "Role already assigned.",
                        "Error during the assignment of the role, the user already has this role.")
                );
            }
            user.getRoles().add(role);
            userRepository.save(user);

            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                    "User role was successfully assigned.",
                    String.format("The user %s was successfully removed the role %s.", user.getUsername(), role.getName()));
            
            return ResponseEntity.ok(messageResponse);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getClass().getName(),
                    e.getStackTrace()[0].toString())
            );
        }        
        
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/role/{roleId}/user/{userId}")
    @AuthAPIDocs.RemoveRole
    public ResponseEntity<?> removeRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Long userId) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loggedInUser = (UserDetailsImpl) authentication.getPrincipal();
            Role role = roleRepository.findById(roleId).orElse(null);

            if (loggedInUser.getId().equals(userId) && role.getName().equals("ADMIN")) {
                return ResponseEntity.badRequest().body(
                    new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                        "Cannot remove role ADMIN from self.",
                        "Error during the removal of the role ADMIN, a user cannot remove a role ADMIN from themselves.")
                );
            }
            
            User user = userRepository.findById(userId).orElse(null);

            if(!user.getRoles().contains(role)) {
                return ResponseEntity.badRequest().body(
                    new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                        "Role not assigned.",
                        "Error during the removal of the role, the user does not have this role.")
                );
            }
            user.getRoles().remove(role);
            userRepository.save(user);

            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                    "User role was successfully removed.",
                    String.format("The user %s was successfully removed the role %s.", user.getUsername(), role.getName()));
            
            return ResponseEntity.ok(messageResponse);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getClass().getName(),
                    e.getStackTrace()[0].toString())
            );
        }
    }

    @PutMapping("/password/update")
    @AuthAPIDocs.ChangePassword
    public ResponseEntity<?> changePassword(@RequestHeader("password") String password) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loggedInUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(loggedInUser.getId()).orElse(null);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);

        return ResponseEntity.ok(new ServerMessageResponse(HttpStatus.OK,
                "Password updated successfully.",
                "The password was successfully updated."));
    }
    
    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    @AuthAPIDocs.GetRoles
    public ResponseEntity<?> getRoles() {

        Set<RoleDTO> rolesDTO = new HashSet<>();
        List<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            rolesDTO.add(new RoleDTO(role));
        }

        return ResponseEntity.ok(rolesDTO);
    }

    


}
