package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;

/**
 * The interface User service.
 */
public interface IUserService {
    
    /**
     * Find user by username
     *
     * @param userName the username
     * @return the userDto
     * @throws UserNotFoundException the user not found exception
     */
    UserDto findByUserName(String userName) throws UserNotFoundException;

    /**
     * Add a user in the database
     *
     * @param userDto the user to add
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void addUser(UserDto userDto) throws UserWithSameUserNameExistsException;
}
