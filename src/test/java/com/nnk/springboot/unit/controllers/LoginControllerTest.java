package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Unit test class for the LoginController class.
 * Without security configuration
 */
@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;


    /**
     * Test method : login
     * When: GET /app/login
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenLogin_thenReturnPage() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * Test method : getAllUserArticles
     * When: GET /app/secure/article-details
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenGetAllUserArticles_thenReturnPage() throws Exception {
        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
        verify(userService, times(1)).findAll();
    }

    /**
     * Test method : error
     * When: GET /app/error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenError_thenReturnPage() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"));
    }
}
