package com.nnk.springboot.exceptions;

import lombok.extern.slf4j.Slf4j;


/**
 * UserWithSameUserNameExistsException Class
 */
@Slf4j
public class UserWithSameUserNameExistsException extends RuntimeException {

    /**
     * Constructor
     *
     * @param userName (String) : User name
     */
    public UserWithSameUserNameExistsException(String userName) {
        super("The username '" + userName + "' already exist.");
        log.error("====> <exception> UserWithSameUserNameExistsException : {} <====", userName);
    }
}
