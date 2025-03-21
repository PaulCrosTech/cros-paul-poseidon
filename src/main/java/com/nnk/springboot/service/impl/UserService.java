package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Find a user by user id
     *
     * @param id the id of the user
     * @return the userDto
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public UserDto findByUserId(Integer id) throws UserNotFoundException {
        log.debug("====> find user by user id {} <====", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
        return userMapper.toUserDto(user);
    }

    /**
     * Create a new user
     *
     * @param userDto the user to add
     * @throws UserWithSameUserNameExistsException if a user with the same username already exists
     */
    @Transactional
    @Override
    public void addUser(UserDto userDto) throws UserWithSameUserNameExistsException {
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
    public void updateUser(UserDto userDto) throws UserWithSameUserNameExistsException {
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
     * @throws UserNotFoundException if the user is not found
     */
    @Transactional
    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("Invalid user Id:" + id)
                );
        userRepository.delete(user);
    }

}
