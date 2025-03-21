package com.nnk.springboot.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception for not found entity
 */
@Slf4j
public class EntityNotFoundException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message (String) : Message of the exception
     */
    public EntityNotFoundException(String message) {
        super(message);
        log.error("====> <exception> NotFoundException for entity - {} <====", message);
    }
}
