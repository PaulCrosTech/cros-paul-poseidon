package com.nnk.springboot.exceptions;

import lombok.extern.slf4j.Slf4j;


/**
 * Exception for entity not found
 */
@Slf4j
public class EntityMissingException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message (String) : Message of the exception
     */
    public EntityMissingException(String message) {
        super(message);
        log.error("====> <exception> NotFoundException for entity - {} <====", message);
    }
}
