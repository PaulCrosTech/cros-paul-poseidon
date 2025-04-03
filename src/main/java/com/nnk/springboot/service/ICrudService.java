package com.nnk.springboot.service;

import com.nnk.springboot.exceptions.EntityMissingException;

import java.util.List;

/**
 * Interface for CRUD operations.
 *
 * @param <T> the type of the DTO
 */
public interface ICrudService<T> {
    
    /**
     * Find all.
     *
     * @return the list of all DTOs
     */
    List<T> findAll();

    /**
     * Find by id.
     *
     * @param id the id of the entity
     * @return the DTO
     * @throws EntityMissingException if the entity is not found
     */
    T findById(Integer id) throws EntityMissingException;

    /**
     * Create a new entity.
     *
     * @param t the DTO to create
     */
    void create(T t);

    /**
     * Update an existing entity.
     *
     * @param t the DTO to update
     * @throws EntityMissingException if the entity is not found
     */
    void update(T t) throws EntityMissingException;


    /**
     * Delete an entity by id.
     *
     * @param id the id of the entity to delete
     * @throws EntityMissingException if the entity is not found
     */
    void delete(Integer id) throws EntityMissingException;

}
