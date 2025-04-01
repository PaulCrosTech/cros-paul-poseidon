package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The User service
 */
@Service
@Slf4j
public class UserService extends AbstractCrudService<User, UserDto> {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor
     *
     * @param mapper     the mapper
     * @param repository the repository
     */
    public UserService(@Qualifier("userMapperImpl") UserMapper mapper,
                       UserRepository repository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(mapper, repository);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.repository = repository;
    }

    /**
     * Find all users except the user with the given username
     *
     * @param username the username of the user to exclude
     * @return the list of users
     */
    public List<UserDto> findAllExceptUserWithUsername(String username) {
        log.debug("====> find all users except the user with username '{}' <====", username);
        List<User> users = repository.findAllByUsernameNot(username);
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(mapper.toDto(user)));
        return userDtos;
    }

    /**
     * Create a new user
     *
     * @param userDto the user to add
     * @throws UserWithSameUserNameExistsException if a user with the same username already exists
     */
    @Transactional
    @Override
    public void create(UserDto userDto) throws UserWithSameUserNameExistsException {
        log.debug("====> creating new user {} <====", userDto.getUsername());

        if (repository.existsByUsername(userDto.getUsername())) {
            throw new UserWithSameUserNameExistsException(userDto.getUsername());
        }

        User user = mapper.toDomain(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        repository.save(user);
        log.debug("====> user created <====");

    }

    /**
     * Update a user
     *
     * @param userDto the user to update
     * @throws UserWithSameUserNameExistsException if a user with the same username already exists
     */
    @Transactional
    @Override
    public void update(UserDto userDto) throws UserWithSameUserNameExistsException {
        log.debug("====> update the user with id {} <====", userDto.getId());

        if (repository.existsByUsernameAndIdNot(userDto.getUsername(), userDto.getId())) {
            throw new UserWithSameUserNameExistsException(userDto.getUsername());
        }

        User user = mapper.toDomain(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        repository.save(user);
        log.debug("====> user updated <====");
    }


}