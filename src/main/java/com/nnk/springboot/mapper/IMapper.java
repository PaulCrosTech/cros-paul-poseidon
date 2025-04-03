package com.nnk.springboot.mapper;

/**
 * The IMapper interface
 *
 * @param <Entity> the entity
 * @param <Dto>    the DTO
 */
public interface IMapper<Entity, Dto> {


    /**
     * Convert a DTO to an Entity
     *
     * @param dto the DTO
     * @return the Entity
     */
    Entity toDomain(Dto dto);

    /**
     * Convert an Entity to a DTO
     *
     * @param e
     * @return the DTO
     */
    Dto toDto(Entity e);

}
