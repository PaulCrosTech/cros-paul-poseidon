package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
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
public class UserService implements IUserService {


    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor
     *
     * @param userRepository        the user repository
     * @param bCryptPasswordEncoder the BCryptPasswordEncoder
     * @param userMapper            the user mapper
     */
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Find user by id
     *
     * @param id the id of the user
     * @return the userDto
     * @throws EntityMissingException if the user is not found
     */
    @Override
    public UserDto findById(Integer id) throws EntityMissingException {
        log.debug("====> find user by user id {} <====", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("User not found with id : " + id));
        return userMapper.toUserDto(user);
    }

    /**
     * Find all users
     *
     * @return the list of users
     */
    @Override
    public List<UserDto> findAll() {
        log.debug("====> find all users <====");
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(userMapper.toUserDto(user)));
        return userDtos;
    }


    /**
     * Find all users except the user with the given username
     *
     * @param username the username of the user to exclude
     * @return the list of users
     */
    @Override
    public List<UserDto> findAllExceptUserWithUsername(String username) {
        log.debug("====> find all users except the user with username '{}' <====", username);
        List<User> users = userRepository.findAllByUsernameNot(username);
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(userMapper.toUserDto(user)));
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

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserWithSameUserNameExistsException(userDto.getUsername());
        }

        User user = userMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
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

        if (userRepository.existsByUsernameAndIdNot(userDto.getUsername(), userDto.getId())) {
            throw new UserWithSameUserNameExistsException(userDto.getUsername());
        }

        User user = userMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        log.debug("====> user updated <====");
    }


    /**
     * Delete a user
     *
     * @param id the id of the user to delete
     * @throws EntityMissingException if the user is not found
     */
    @Transactional
    @Override
    public void delete(Integer id) throws EntityMissingException {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Invalid user Id:" + id)
                );
        userRepository.delete(user);
    }

}
