package com.nnk.springboot.integration.controller;

import com.nnk.springboot.integration.config.AbstractContainerDB;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test class for the LoginController class.
 */
public class LoginControllerIT extends AbstractContainerDB {


    /**
     * Test method : login
     * When GET /app/login
     * Then login page is returned
     *
     * @throws Exception exception
     */
    @Test
    public void whenLogin_thenReturnPage() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * Test method : login
     * Given Existing username and password
     * When POST /app/login
     * Then user is authenticated and redirected to private page
     *
     * @throws Exception exception
     */
    @Test
    public void givenExistingUser_whenLogin_thenAuthenticatedAndRedirect() throws Exception {

        mockMvc.perform(post("/app/login")
                        .param("username", "user.user")
                        .param("password", "Password@1")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    /**
     * Test method : login
     * Given Non-existing username and password
     * When POST /app/login
     * Then user is not authenticated and redirected to login page with error
     *
     * @throws Exception exception
     */
    @Test
    public void givenNonExistingUser_whenLogin_thenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/app/login")
                        .param("username", "badUsername")
                        .param("password", "badPassword@1")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error"));
    }

}
