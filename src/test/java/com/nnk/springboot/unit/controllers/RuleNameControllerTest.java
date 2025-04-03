package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.impl.RuleNameController;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.impl.RuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test class for the RuleNameController class.
 * Without security configuration
 */
@WebMvcTest(controllers = RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleService ruleService;

    /**
     * Test method : home
     * When: GET /ruleName/list
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenHome_thenReturnPage() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());
    }


    /**
     * Test method : addRuleForm
     * When: GET /ruleName/add
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenAddRuleForm_thenReturnPage() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /ruleName/validate
     * Then: Rule is created and redirect to /ruleName/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenValidate_thenRuleIsCreatedAndRedirect() throws Exception {

        // Given
        RuleDto ruleDto = new RuleDto();
        ruleDto.setName("name");
        ruleDto.setDescription("description");
        ruleDto.setJson("json");
        ruleDto.setTemplate("template");
        ruleDto.setSqlStr("sqlStr");
        ruleDto.setSqlPart("sqlPart");


        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .param("name", ruleDto.getName())
                        .param("description", ruleDto.getDescription())
                        .param("json", ruleDto.getJson())
                        .param("template", ruleDto.getTemplate())
                        .param("sqlStr", ruleDto.getSqlStr())
                        .param("sqlPart", ruleDto.getSqlPart())
        );

        // Then
        verify(ruleService, times(1)).create(ruleDto);
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/ruleName/list"));

    }

    /**
     * Test method : validate
     * Given: form with invalid data
     * When: POST /ruleName/validate
     * Then: Return the add page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenValidate_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""));


        // Then
        verify(ruleService, times(0)).create(any(RuleDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : showUpdateForm
     * Given: valid id
     * When: GET /ruleName/update/{id}
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenShowUpdateForm_thenReturnPage() throws Exception {

        // Given
        when(ruleService.findById(any(Integer.class))).thenReturn(new RuleDto());

        // When
        ResultActions resultActions = mockMvc.perform(get("/ruleName/update/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    /**
     * Test method : showUpdateForm
     * Given: invalid id
     * When: GET /ruleName/update/{id}
     * Then: Redirect to /ruleName/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenShowUpdateForm_thenRedirectWithError() throws Exception {

        // Given
        when(ruleService.findById(any(Integer.class))).thenThrow(new EntityMissingException("Rule not found with id : 1"));

        // When
        ResultActions resultActions = mockMvc.perform(get("/ruleName/update/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }


    /**
     * Test method : updateRuleName
     * Given: valid form
     * When: POST /ruleName/update/{id}
     * Then: Rule is updated and redirect to /ruleName/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenUpdateRuleName_thenRuleIsUpdatedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/update/1")
                        .with(csrf().asHeader())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"));

        // Then
        verify(ruleService, times(1)).update(any(RuleDto.class));
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    /**
     * Test method : updateRuleName
     * Given: invalid form
     * When: POST /ruleName/update/{id}
     * Then: Return the update page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenUpdateRuleName_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/update/1")
                        .with(csrf().asHeader())
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""));

        // Then
        verify(ruleService, times(0)).update(any(RuleDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : updateRuleName
     * Given: valid form and invalid rule id
     * When: POST /ruleName/update/{id}
     * Then: Redirect to /ruleName/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormInvalidRuleId_whenUpdateRuleName_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Bid not found with id : 1")).when(ruleService).update(any(RuleDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/update/1")
                        .with(csrf().asHeader())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : deleteRuleName
     * Given: valid id
     * When: GET /ruleName/delete/{id}
     * Then: Rule is deleted and redirect to /ruleName/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenDeleteRuleNAme_thenRuleIsDeletedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(get("/ruleName/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

    }

    /**
     * Test method : deleteRuleName
     * Given: invalid id
     * When: GET /ruleName/delete/{id}
     * Then: Redirect to /ruleName/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenDeleteRule_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Rule not found with id : 1")).when(ruleService).delete(any(Integer.class));

        // When
        ResultActions resultActions = mockMvc.perform(get("/ruleName/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

}
