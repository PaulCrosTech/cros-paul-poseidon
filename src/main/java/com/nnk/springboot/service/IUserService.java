package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;

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
    // TODO : modifier en findById
    UserDto findByUserId(Integer id) throws UserNotFoundException;

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
