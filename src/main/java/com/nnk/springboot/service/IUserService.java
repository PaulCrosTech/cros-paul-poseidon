package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;

import java.util.List;

/**
 * The interface User service.
 */
public interface IUserService {


    /**
     * Find user by user id
     *
     * @param id the id of the user to find
     * @return the userDto
     * @throws EntityMissingException the user not found exception
     */
    UserDto findById(Integer id) throws EntityMissingException;

    /**
     * Find all users except the user with the given username
     *
     * @return the list of userDto
     */
    List<UserDto> findAllExceptUserWithUsername(String username);

    /**
     * Create a user in the database
     *
     * @param userDto the user to add
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void create(UserDto userDto) throws UserWithSameUserNameExistsException;


    /**
     * Update user
     *
     * @param userDto the user to update
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void update(UserDto userDto) throws UserWithSameUserNameExistsException;

    /**
     * Delete a user from the database
     *
     * @param id the id of the user to delete
     * @throws EntityMissingException the user not found exception
     */
    void delete(Integer id) throws EntityMissingException;
}
