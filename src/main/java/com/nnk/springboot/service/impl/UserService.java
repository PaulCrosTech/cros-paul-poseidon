package com.nnk.springboot.service.impl;

import com.nnk.springboot.entity.User;
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
     * Find a user by username
     *
     * @param userName the username
     * @return the userDto
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public UserDto findByUserName(String userName) throws UserNotFoundException {
        log.debug("====> find user by user name {} <====", userName);
        User dbUser = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with username : " + userName));
        return userMapper.toUserDto(dbUser);
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

        if (isUserExistWithSameUserName(userDto.getUsername())) {
            throw new UserWithSameUserNameExistsException(userDto.getUsername());
        }

        User user = userMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        log.debug("====> user created <====");

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

    /**
     * Check if a user exists with the same username
     *
     * @param userName the username to check
     * @return true if the user exists, false otherwise
     */
    private boolean isUserExistWithSameUserName(String userName) {
        try {
            findByUserName(userName);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
