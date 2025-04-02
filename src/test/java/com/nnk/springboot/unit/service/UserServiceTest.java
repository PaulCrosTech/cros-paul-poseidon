package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


/**
 * The UserServiceTest unit test
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        userService = new UserService(userMapper, userRepository, bCryptPasswordEncoder);
    }
    
    /**
     * Test findAllExceptUserWithUsername
     * Given: A username
     * When: findAllExceptUserWithUsername
     * Then: Return the list of UserDto
     */
    @Test
    public void givenUsername_whenFindAllExceptUsername_thenReturnListOfUserDto() {
        // Given
        String username = "username";
        when(userRepository.findAllByUsernameNot(username)).thenReturn(List.of(new User()));
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());

        // When
        List<UserDto> userDtoList = userService.findAllExceptUserWithUsername(username);

        // Then
        assertEquals(List.of(new UserDto()), userDtoList);
    }


    /**
     * Test create
     * Given: A UserDto
     * When: create
     * Then: User is created
     */
    @Test
    public void givenUserDto_whenCreate_thenUserIsCreated() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("passwordClear");

        User user = new User();

        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);

        when(userMapper.toDomain(userDto)).thenReturn(user);
        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn("passwordEncoded");

        // When
        userService.create(userDto);

        // Then
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Test create
     * Given: A UserDto with username already used
     * When: create
     * Then: Throw UserWithSameUserNameExistsException
     */
    @Test
    public void givenUserDtoWithUsernameAlreadyUsed_whenUpdate_thenThrowUserWithSameUserNameExistsException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUsername("usernameAlreadyUsed");

        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(true);

        // When & Then
        assertThrows(UserWithSameUserNameExistsException.class,
                () -> userService.create(userDto)
        );
    }

    /**
     * Test update
     * Given: A UserDto with valid username
     * When: update
     * Then: User is updated
     */
    @Test
    public void givenExistingUserDto_whenUpdate_thenUserIsUpdated() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("username");

        User user = new User();
        user.setId(1);

        when(userRepository.existsByUsernameAndIdNot(userDto.getUsername(), userDto.getId())).thenReturn(false);
        when(userMapper.toDomain(userDto)).thenReturn(user);
        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn("passwordEncoded");

        // When
        userService.update(userDto);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Test update
     * Given: A UserDto with username already used
     * When: update
     * Then: Throw UserWithSameUserNameExistsException
     */
    @Test
    public void givenExistingUserDtoWIthUsernameAlreadyUsed_whenUpdate_thenThrowUserWithSameUserNameExistsException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("username");

        when(userRepository.existsByUsernameAndIdNot(userDto.getUsername(), userDto.getId())).thenReturn(true);

        // When
        assertThrows(UserWithSameUserNameExistsException.class,
                () -> userService.update(userDto)
        );

        // Then
        verify(userRepository, times(0)).save(any(User.class));
    }

    /**
     * Test delete
     * Given: A User Id
     * When: delete
     * Then: User Deleted
     */
    @Test
    public void givenExistingUserId_whenDelete_thenUserIsDeleted() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));

        // When
        userService.delete(anyInt());

        // Then
        verify(userRepository, times(1)).delete(any(User.class));
    }

    /**
     * Test delete
     * Given: A non-existing User Id
     * When: delete
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingUserId_whenDelete_thenThrowEntityMissingException() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> userService.delete(anyInt())
        );
    }

}
