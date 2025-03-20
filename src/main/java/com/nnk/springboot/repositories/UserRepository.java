package com.nnk.springboot.repositories;

import com.nnk.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find by username
     *
     * @param username the username
     * @return the user (optional)
     */
    Optional<User> findByUsername(String username);

    /**
     * Find by user id
     *
     * @param userId the user id
     * @return the user (optional)
     */
    Optional<User> findByUserId(Integer userId);

    /**
     * Verify if a user exist with username and different user id
     *
     * @param username the username
     * @param userId   the user id
     * @return the boolean
     */
    boolean existsByUsernameAndUserIdNot(String username, Integer userId);

    /**
     * Verify if a user exist with username
     *
     * @param username the username
     * @return the boolean
     */
    boolean existsByUsername(String username);
}
