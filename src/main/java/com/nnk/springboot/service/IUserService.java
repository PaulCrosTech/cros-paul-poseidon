package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;

import java.util.List;

/**
 * The interface User service.
 */
public interface IUserService {

    /**
     * Find user by user id
     *
     * @param id the id of the user
     * @return the userDto
     * @throws UserNotFoundException the user not found exception
     */
    UserDto findById(Integer id) throws UserNotFoundException;

    /**
     * Find all users except the user with the given username
     *
     * @return the list of userDto
     */
    List<UserDto> findAllExceptUserWithUsername(String username);

    /**
     * Add a user in the database
     *
     * @param userDto the user to add
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void addUser(UserDto userDto) throws UserWithSameUserNameExistsException;


    /**
     * Update user
     *
     * @param userDto the user to update
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void updateUser(UserDto userDto) throws UserWithSameUserNameExistsException;

    /**
     * Delete user
     *
     * @param id the id of the user to delete
     * @throws UserNotFoundException the user not found exception
     */
    void deleteUser(Integer id) throws UserNotFoundException;
}
