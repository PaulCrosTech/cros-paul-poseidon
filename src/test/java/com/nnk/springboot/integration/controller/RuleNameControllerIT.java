package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.integration.config.AbstractContainerDB;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the RuleNameController class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin.admin", roles = {"ADMIN"})
public class RuleNameControllerIT extends AbstractContainerDB {

    @Autowired
    private RuleNameRepository ruleNameRepository;


    /**
     * Get the last Rule created in the database
     * Utility method
     *
     * @return Rule or null
     */
    private Rule getLastRule() {
        return ruleNameRepository.findAll(
                        PageRequest.of(0, 1,
                                Sort.by(Sort.Direction.DESC, "id"))
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /ruleName/validate
     * Then: Rule is created and redirect to /ruleName/list
     *
     * @throws Exception exception
     */
    @Order(1)
    @Test
    public void givenValidForm_whenValidate_thenRuleIsCreatedAndRedirect() throws Exception {

        // Given
        RuleDto ruleDto = new RuleDto();
        ruleDto.setName("Name Created");
        ruleDto.setDescription("Description Created");
        ruleDto.setJson("Json Created");
        ruleDto.setTemplate("Template Created");
        ruleDto.setSqlPart("Sql Part Created");
        ruleDto.setSqlStr("Sql Str Created");

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .param("name", ruleDto.getName())
                        .param("description", ruleDto.getDescription())
                        .param("json", ruleDto.getJson())
                        .param("template", ruleDto.getTemplate())
                        .param("sqlStr", ruleDto.getSqlStr())
                        .param("sqlPart", ruleDto.getSqlPart()));

        // Then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/ruleName/list"));

        Rule ruleCreated = getLastRule();
        assertNotNull(ruleCreated);
        assertEquals(ruleDto.getName(), ruleCreated.getName());
        assertEquals(ruleDto.getDescription(), ruleCreated.getDescription());
        assertEquals(ruleDto.getJson(), ruleCreated.getJson());
        assertEquals(ruleDto.getTemplate(), ruleCreated.getTemplate());
        assertEquals(ruleDto.getSqlStr(), ruleCreated.getSqlStr());
        assertEquals(ruleDto.getSqlPart(), ruleCreated.getSqlPart());
    }


    /**
     * Test method : updateRuleName
     * Given: form with valid data
     * When: POST /ruleName/update/{id}
     * Then: Rule is updated and redirect to /ruleName/list
     *
     * @throws Exception exception
     */
    @Order(2)
    @Test
    public void givenValidForm_whenUpdateRuleName_thenRuleUpdatedAndRedirect() throws Exception {

        // Given
        Rule rule = getLastRule();
        if (rule == null) {
            throw new AssertionError("No Rule found");
        }

        String name = "Name Updated";
        String description = "Description Updated";
        String json = "Json Updated";
        String template = "Template Updated";
        String sqlPart = "Sql Part Updated";
        String sqlStr = "Sql Str Updated";

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/ruleName/update/" + rule.getId())
                        .with(csrf().asHeader())
                        .param("name", name)
                        .param("description", description)
                        .param("json", json)
                        .param("template", template)
                        .param("sqlStr", sqlStr)
                        .param("sqlPart", sqlPart));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        Rule ruleUpdated = ruleNameRepository
                .findById(rule.getId())
                .orElseThrow(() -> new AssertionError("Rule : '" + rule.getId() + "' notfound"));

        assertEquals(name, ruleUpdated.getName());
        assertEquals(description, ruleUpdated.getDescription());
        assertEquals(json, ruleUpdated.getJson());
        assertEquals(template, ruleUpdated.getTemplate());
        assertEquals(sqlPart, ruleUpdated.getSqlPart());
        assertEquals(sqlStr, ruleUpdated.getSqlStr());

    }


    /**
     * Test method : deleteRuleName
     * Given: valid id
     * When: GET /ruleName/delete/{id}
     * Then: Rule is deleted and redirect to /ruleName/list
     *
     * @throws Exception exception
     */
    @Order(3)
    @Test
    public void givenValidId_whenDeleteRuleName_thenRuleIsDeletedAndRedirect() throws Exception {

        // Given
        Rule rule = getLastRule();
        if (rule == null) {
            throw new AssertionError("No Rule found");
        }

        // When
        ResultActions resultActions = mockMvc.perform(
                get("/ruleName/delete/" + rule.getId())
        );

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        Optional<Rule> ruleDeleted = ruleNameRepository.findById(rule.getId());
        assertEquals(Optional.empty(), ruleDeleted);
    }

}
