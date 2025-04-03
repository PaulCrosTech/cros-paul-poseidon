package com.nnk.springboot.service;

import com.nnk.springboot.dto.AbstractDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.IMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract CRUD service
 *
 * @param <E>   the Entity
 * @param <Dto> the Dto
 */
@Slf4j
public abstract class AbstractCrudService<E, Dto extends AbstractDto> implements ICrudService<Dto> {

    protected final IMapper<E, Dto> mapper;
    protected final JpaRepository<E, Integer> repository;

    /**
     * Constructor
     *
     * @param repository the repository
     * @param mapper     the mapper
     */
    public AbstractCrudService(IMapper<E, Dto> mapper,
                               JpaRepository<E, Integer> repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Find all entities
     *
     * @return the list of Dto
     */
    @Override
    public List<Dto> findAll() {
        log.debug("====> find all <====");
        List<E> eList = repository.findAll();

        List<Dto> dtoList = new ArrayList<>();

        eList.forEach(o -> {
            dtoList.add(mapper.toDto(o));
        });

        return dtoList;
    }

    /**
     * Find an entity by id
     *
     * @param id the id of the entity
     * @return the Dto
     * @throws EntityMissingException if the entity is not found
     */
    @Override
    public Dto findById(Integer id) throws EntityMissingException {
        log.debug("====> find bid by id {} <====", id);
        E e = repository.findById(id)
                .orElseThrow(() -> new EntityMissingException("Not found with id : " + id));
        return mapper.toDto(e);
    }

    /**
     * Create an entity
     *
     * @param dto the DTO to create
     */
    @Transactional
    @Override
    public void create(Dto dto) {
        log.debug("====> creating <====");

        E e = mapper.toDomain(dto);
        repository.save(e);

        log.debug("====> created <====");
    }

    /**
     * Update an entity
     *
     * @param dto the DTO to update
     * @throws EntityMissingException if the entity is not found
     */
    @Transactional
    @Override
    public void update(Dto dto) throws EntityMissingException {
        log.debug("====> update id {} <====", dto.getId());

        E entity = repository.findById(dto.getId())
                .orElseThrow(
                        () -> new EntityMissingException("Not found with id : " + dto.getId())
                );

        Class<?> reflexionDto = dto.getClass();
        Field[] fieldsDto = reflexionDto.getDeclaredFields();
        for (Field fieldDto : fieldsDto) {
            try {
                fieldDto.setAccessible(true);

                Field field = entity.getClass().getDeclaredField(fieldDto.getName());
                field.setAccessible(true);

                field.set(entity, fieldDto.get(dto));
                log.debug("====> Update field {} : ok <====", fieldDto.getName());
            } catch (NoSuchFieldException e) {
                // Field not found in the entity class, ignore
                log.debug("====> Update field {} : ignore <====", fieldDto.getName());
            } catch (IllegalAccessException e) {
                log.debug("====> Update field {} : error {} <====", fieldDto.getName(), e.getMessage());
                log.error("Error updating field: {}", fieldDto.getName(), e);
            }
        }
        repository.save(entity);
        log.debug("====> Updated okay<====");
    }

    /**
     * Delete an entity
     *
     * @param id the id of the entity to delete
     * @throws EntityMissingException if the entity is not found
     */
    @Transactional
    @Override
    public void delete(Integer id) throws EntityMissingException {
        E e = repository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Not found with id : " + id)
                );
        repository.delete(e);
    }
}
