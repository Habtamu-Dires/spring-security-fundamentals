package com.example.repository;

import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);
}


/**
 * Common Mistake - using stereotype annotations in interfaces
 * Stereotype annotations are used to crete instance of a given 'class' into a spring context.
 * so never use stereotype annotations on interfaces and abstract class
 */