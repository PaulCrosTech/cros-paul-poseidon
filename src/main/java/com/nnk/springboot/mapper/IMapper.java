package com.nnk.springboot.mapper;

/**
 * The IMapper interface
 *
 * @param <Entity>
 * @param <Dto>
 */
public interface IMapper<Entity, Dto> {

    /**
     * Convert a DTO to an Entity
     *
     * @param dto the Dto
     * @return the Entity
     */
    Entity toDomain(Dto dto);

    /**
     * Convert an Entity to a DTO
     *
     * @param e the Entity
     * @return the Dto
     */
    Dto toDto(Entity e);

}
