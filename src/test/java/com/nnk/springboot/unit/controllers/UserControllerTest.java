package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.impl.UserController;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test class for the UserController class.
 * Without security configuration
 */
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    /**
     * Test method : home
     * When: GET /user/list
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenHome_thenReturnPage() throws Exception {

        List<UserDto> users = List.of(new UserDto());
        when(userService.findAllExceptUserWithUsername(anyString())).thenReturn(users);

        User user = new User("username", "password", List.of());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : addUser
     * When: GET /user/add
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenAddUser_thenReturnPage() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /user/validate
     * Then: User is created and redirect to /user/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenValidate_thenUserIsCreatedAndRedirect() throws Exception {

        // Given
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("Password@1");
        userDto.setRole("USER");
        userDto.setFullname("Fullname");

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/validate")
                        .with(csrf().asHeader())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole()));

        // Then
        verify(userService, times(1)).create(userDto);
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/user/list"));

    }

    /**
     * Test method : validate
     * Given: form with invalid data
     * When: POST /user/validate
     * Then: Return the page with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenValidate_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/validate")
                        .with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""));


        // Then
        verify(userService, times(0)).create(any(UserDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : validate
     * Given: valid form with username already used
     * When: POST /user/validate
     * Then: Redirect to /user/add with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormWithUsernameAlreadyUsed_whenValidate_thenRedirectWithError() throws Exception {

        // Given
        UserDto userDto = new UserDto();
        userDto.setUsername("usernameAlreadyUsed");
        userDto.setPassword("Password@1");
        userDto.setFullname("Fullname");
        userDto.setRole("USER");

        doThrow(new UserWithSameUserNameExistsException("The username aready exist.")).when(userService).create(userDto);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/validate")
                        .with(csrf().asHeader())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole()));

        // Then
        verify(userService, times(1)).create(userDto);
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors());

    }

    /**
     * Test method : showUpdateForm
     * Given: valid id
     * When: GET /user/update/{id}
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenShowUpdateForm_thenReturnPage() throws Exception {

        // Given
        when(userService.findById(any(Integer.class))).thenReturn(new UserDto());

        // When
        ResultActions resultActions = mockMvc.perform(get("/user/update/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

    }

    /**
     * Test method : showUpdateForm
     * Given: invalid id
     * When: GET /user/update/{id}
     * Then: Redirect to /user/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenShowUpdateForm_thenRedirectWithError() throws Exception {

        // Given
        when(userService.findById(any(Integer.class))).thenThrow(new EntityMissingException("User not found with id : 1"));

        // When
        ResultActions resultActions = mockMvc.perform(get("/user/update/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : updateUser
     * Given: form with valid data
     * When: POST /user/update/{id}
     * Then: User is updated and redirect to /user/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenUpdateUser_thenUserIsUpdatedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/update/1")
                        .with(csrf().asHeader())
                        .param("username", "username")
                        .param("password", "Password@1")
                        .param("fullname", "fullname")
                        .param("role", "USER"));

        // Then
        verify(userService, times(1)).update(any(UserDto.class));
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    /**
     * Test method : updateUser
     * Given: form with invalid data
     * When: POST /user/update/{id}
     * Then: Return the page with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenUpdateUser_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/update/1")
                        .with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""));

        // Then
        verify(userService, times(0)).update(any(UserDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : updateUser
     * Given: valid form and invalid user id
     * When: POST /user/update/{id}
     * Then: Redirect to /user/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormInvalidUserId_whenUpdateUser_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("User not found with id : 1")).when(userService).update(any(UserDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/update/1")
                        .with(csrf().asHeader())
                        .param("username", "username")
                        .param("password", "Password@1")
                        .param("fullname", "fullname")
                        .param("role", "USER"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : updateUser
     * Given: valid form with username already used
     * When: POST /user/update/{id}
     * Then: Redirect to /user/update with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormWithUsernameAlreadyUsed_whenUpdateUser_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new UserWithSameUserNameExistsException("Username already used")).when(userService).update(any(UserDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/user/update/1")
                        .with(csrf().asHeader())
                        .param("username", "usernameAldredyUsed")
                        .param("password", "Password@1")
                        .param("fullname", "fullname")
                        .param("role", "USER"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors());
    }


    /**
     * Test method : deleteUser
     * Given: valid id
     * When: GET /user/delete/{id}
     * Then: User is deleted and redirect to /user/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenDeleteUser_thenUserIsDeletedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(get("/user/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

    }

    /**
     * Test method : deleteUser
     * Given: invalid id
     * When: GET /user/delete/{id}
     * Then: Redirect to /user/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenDeleteUser_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("User not found with id : 1")).when(userService).delete(any(Integer.class));

        // When
        ResultActions resultActions = mockMvc.perform(get("/user/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

}
