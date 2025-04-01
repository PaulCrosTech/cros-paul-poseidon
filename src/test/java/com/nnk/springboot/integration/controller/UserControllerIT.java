package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.integration.config.AbstractContainerDB;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test class for the UserController class.
 * With ADMIN role
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin.admin", roles = "ADMIN")
public class UserControllerIT extends AbstractContainerDB {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /user/validate
     * Then: User is created and redirect to /user/list
     *
     * @throws Exception if an error occurs
     */
    @Order(1)
    @Test
    public void givenValidForm_whenValidate_thenUserIsCreatedAndRedirect() throws Exception {
        // Given
        String username = "test.test";
        String password = "Password@1";
        String fullname = "Test Fullname";
        String role = "USER";

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/validate")
                        .with(csrf().asHeader())
                        .param("username", username)
                        .param("password", password)
                        .param("fullname", fullname)
                        .param("role", role));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));


        User userActual = userRepository.findByUsername("test.test")
                .orElseThrow(() -> new AssertionError("User test.test not found"));

        assertEquals(username, userActual.getUsername());
        assertEquals(fullname, userActual.getFullname());
        assertEquals(role, userActual.getRole());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, userActual.getPassword()));
    }


    /**
     * Test method : validate
     * Given: form with username already used
     * When: POST /user/validate
     * Then: UserWithSameUserNameExistsException is thrown and return to /user/add
     *
     * @throws Exception if an error occurs
     */
    @Order(2)
    @Test
    public void givenValidFormWithUsernameAlreadyUsed_whenValidate_thenError() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUsername("test.test");
        userDto.setPassword("Password@1");
        userDto.setFullname("Test Fullname");
        userDto.setRole("USER");

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/validate")
                        .with(csrf().asHeader())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole()));

        // Then
        assertThrows(UserWithSameUserNameExistsException.class, () -> {
            userService.create(userDto);
        });
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    /**
     * Test method : updateUser
     * Given: form with valid data
     * When: POST /user/update/{id}
     * Then: User is updated and redirect to /user/list
     *
     * @throws Exception if an error occurs
     */
    @Order(3)
    @Test
    public void givenValidForm_whenUpdateUser_thenUserIsUpdatedAndRedirect() throws Exception {
        // Given
        User user = userRepository.findByUsername("test.test")
                .orElseThrow(() -> new AssertionError("User not found"));

        String username = "test.test.updated";
        String password = "Password@2";
        String fullname = "Test Updated";
        String role = "USER";

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/update/" + user.getId())
                        .with(csrf().asHeader())
                        .param("username", username)
                        .param("password", password)
                        .param("fullname", fullname)
                        .param("role", "USER"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        User userActual = userRepository.findByUsername(username)
                .orElseThrow(() -> new AssertionError("User : '" + username + "' not found"));

        assertEquals(username, userActual.getUsername());
        assertEquals(fullname, userActual.getFullname());
        assertEquals(role, userActual.getRole());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, userActual.getPassword()));
    }


    /**
     * Test method : deleteUser
     * Given: id of an existing user
     * When: GET /user/delete/{id}
     * Then: User is deleted and redirect to /user/list
     *
     * @throws Exception if an error occurs
     */
    @Order(4)
    @Test
    public void givenValidId_whenDeleteUser_thenUserIsDeleteAndRedirect() throws Exception {
        // Given
        User user = userRepository.findByUsername("test.test.updated")
                .orElseThrow(() -> new AssertionError("User not found"));

        // When+
        ResultActions resultActions = mockMvc.perform(get("/user/delete/" + user.getId()));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        Optional<User> userActual = userRepository.findById(user.getId());
        assertFalse(userActual.isPresent());

    }


}
