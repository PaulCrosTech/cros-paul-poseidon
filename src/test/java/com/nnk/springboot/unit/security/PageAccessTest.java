package com.nnk.springboot.unit.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Page access test
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PageAccessTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test access to login page
     * Given: An unauthenticated user
     * When: Access to login page
     * Then: Return page
     *
     * @throws Exception Exception
     */
    @Test
    public void givenUnauthenticatedUser_whenAccessLoginUrl_thenReturnPage() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk());
    }

    /**
     * Test access to secured url
     * Given: An unauthenticated user
     * When: Access to a secured url
     * Then: Redirect to login page
     *
     * @param url url
     * @throws Exception Exception
     */
    @ParameterizedTest
    @ValueSource(strings = {"/", "/bidList/list", "/trade/list", "/ruleName/list", "/curvePoint/list", "/rating/list"})
    public void givenUnauthenticatedUser_whenAccessToSecuredUrl_thenRedirectToLoginPage(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    /**
     * Test the access to secured url
     * Given: A user with role USER
     * When: Access to an url for ADMIN role
     * Then: Return 403
     *
     * @throws Exception Exception
     */
    @ParameterizedTest
    @ValueSource(strings = {"/app/secure/article-details", "/admin/home", "/user/list", "/user/add", "/user/validate", "/user/update/1", "/user/delete/1"})
    @WithMockUser(username = "userNameUser", roles = {"USER"})
    public void givenUserWithRoleUser_whenAccessToAdminUrl_thenReturnPage403(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError());
    }

}
