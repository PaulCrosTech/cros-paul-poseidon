package com.nnk.springboot.unit.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * CustomUserDetailsService unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsService customUserDetailsService;

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    /**
     * Testing method loadUserByUsername
     * Given valid username
     * When loadUserByUsername
     * Then return user
     */
    @Test
    public void givenValidUsername_whenLoadUserByUsername_thenReturnUser() {
        // Given
        User user = new User();
        user.setUsername("userName");
        user.setPassword("Password@1");
        user.setRole("USER");

        UserDetails userDetailsExpected = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // When
        UserDetails actualUser = customUserDetailsService.loadUserByUsername(user.getUsername());

        // Then
        assertEquals(userDetailsExpected, actualUser);
    }


    /**
     * Testing method loadUserByUsername
     * Given unknow username
     * When loadUserByUsername
     * Then throw UsernameNotFoundException
     */
    @Test
    public void givenUnknowUserName_whenLoadUserByUserName_thenThrowUsernameNotFoundException() {
        // When && Then
        assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("unknowUserName")
        );
    }
}
