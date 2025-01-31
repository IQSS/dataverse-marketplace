package edu.harvard.iq.dataverse.marketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.harvard.iq.dataverse.marketplace.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
