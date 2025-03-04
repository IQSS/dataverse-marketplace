package org.dataverse.marketplace.controller.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.dataverse.marketplace.model.User;
import org.dataverse.marketplace.openapi.annotations.AuthAPIDocs;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.auth.UserDTO;
import org.dataverse.marketplace.repository.UserRepo;
import org.dataverse.marketplace.security.ApplicationRoles;


@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UserRepo userRepository;

    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @GetMapping()
    @AuthAPIDocs.GetUsers
    public ResponseEntity<?> getUsers() {

        Set<UserDTO> usersDTO = new HashSet<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            usersDTO.add(new UserDTO(user));
        }

        return ResponseEntity.ok(usersDTO);
    }

    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @GetMapping("{userId}")
    @AuthAPIDocs.GetUser
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {

        UserDTO userDTO = new UserDTO();
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userDTO = new UserDTO(user);
        } else {
            return ResponseEntity.badRequest().body(new ServerMessageResponse(HttpStatus.BAD_REQUEST,
                    "User not found.",
                    "Error during the retrieval of the user, the user was not found."));
        }

        return ResponseEntity.ok(userDTO);
    }

}
