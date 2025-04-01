package com.nnk.springboot.service;

import com.nnk.springboot.exceptions.EntityMissingException;

import java.util.List;

/**
 * Interface for CRUD operations.
 *
 * @param <Dto> the type of the DTO
 */
public interface ICrudService<Dto> {


    /**
     * Find all.
     *
     * @return the list of all DTOs
     */
    List<Dto> findAll();

    /**
     * Find by id.
     *
     * @param id the id of the entity
     * @return the DTO
     * @throws EntityMissingException if the entity is not found
     */
    Dto findById(Integer id) throws EntityMissingException;

    /**
     * Create a new entity.
     *
     * @param dto the DTO to create
     */
    void create(Dto dto);

    /**
     * Update an existing entity.
     *
     * @param dto the DTO to update
     * @throws EntityMissingException if the entity is not found
     */
    void update(Dto dto) throws EntityMissingException;


    /**
     * Delete an entity by id.
     *
     * @param id the id of the entity to delete
     * @throws EntityMissingException if the entity is not found
     */
    void delete(Integer id) throws EntityMissingException;

}
