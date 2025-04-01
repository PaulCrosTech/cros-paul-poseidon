package com.nnk.springboot.unit.mapper;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UserMapper unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private User user;
    private UserDto userDto;

    private static UserMapper userMapper;

    /**
     * Sets up for all tests.
     */
    @BeforeAll
    public static void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {
        user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFullname("fullname");
        user.setRole("role");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("username");
        userDto.setFullname("fullname");
        userDto.setRole("role");
    }

    /**
     * Test toDto
     * Given: A User
     * When: toDto
     * Then: return UserDto
     */
    @Test
    public void givenUser_whentoDto_thenReturnUserDto() {

        // When
        UserDto userDtoActual = userMapper.toDto(user);

        // Then
        assertEquals(userDto.getId(), userDtoActual.getId());
        assertEquals(userDto.getUsername(), userDtoActual.getUsername());
        assertEquals(userDto.getFullname(), userDtoActual.getFullname());
        assertEquals(userDto.getRole(), userDtoActual.getRole());
    }

    /**
     * Test toDomain
     * Given: A UserDto
     * When: toDomain
     * Then: return User
     */
    @Test
    public void givenUserDto_whentoDomain_thenReturnUser() {
        // When
        User userActual = userMapper.toDomain(userDto);

        // Then
        assertEquals(user.getId(), userActual.getId());
        assertEquals(user.getUsername(), userActual.getUsername());
        assertEquals(user.getFullname(), userActual.getFullname());
        assertEquals(user.getRole(), userActual.getRole());

    }
}
