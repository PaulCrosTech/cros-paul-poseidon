package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.integration.config.AbstractContainerDB;
import com.nnk.springboot.repositories.CurvePointRepository;
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
 * Integration test class for the CurveController class.
 * With ADMIN role
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin.admin", roles = "ADMIN")
public class CurveControllerIT extends AbstractContainerDB {

    @Autowired
    private CurvePointRepository curvePointRepository;


    /**
     * Get the last CurvePoint created in the database
     * Utility method
     *
     * @return CurvePoint or null
     */
    private CurvePoint getLastCurvePoint() {
        return curvePointRepository.findAll(
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
     * When: POST /curvePoint/validate
     * Then: CurvePoint is created and redirect to /curvePoint/list
     *
     * @throws Exception exception
     */
    @Order(1)
    @Test
    public void givenValidForm_whenValidate_thenCurveIsCreatedAndRedirect() throws Exception {

        // Given
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(100);
        curvePointDto.setTerm(111d);
        curvePointDto.setValue(222d);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .param("curveId", curvePointDto.getCurveId().toString())
                        .param("term", curvePointDto.getTerm().toString())
                        .param("value", curvePointDto.getValue().toString()));

        // Then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/curvePoint/list"));

        CurvePoint curvePointCreated = getLastCurvePoint();
        assertNotNull(curvePointCreated);
        assertEquals(curvePointDto.getCurveId(), curvePointCreated.getCurveId());
        assertEquals(curvePointDto.getTerm(), curvePointCreated.getTerm());
        assertEquals(curvePointDto.getValue(), curvePointCreated.getValue());
        assertNotNull(curvePointCreated.getCreationDate());
    }

    /**
     * Test method : update
     * Given: form with valid data
     * When: POST /curvePoint/update/{id}
     * Then: CurvePoint is updated and redirect to /curvePoint/list
     *
     * @throws Exception exception
     */
    @Order(2)
    @Test
    public void givenValidForm_whenUpdateCurvePoint_thenCurveUpdatedAndRedirect() throws Exception {

        // Given
        CurvePoint curvePoint = getLastCurvePoint();
        if (curvePoint == null) {
            throw new AssertionError("No Curve Point found");
        }
        Integer curveId = 127;
        Double term = 666d;
        Double value = 999d;

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/update/" + curvePoint.getId())
                        .with(csrf().asHeader())
                        .param("curveId", curveId.toString())
                        .param("term", term.toString())
                        .param("value", value.toString()));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        CurvePoint curvePointUpdated = curvePointRepository
                .findById(curvePoint.getId())
                .orElseThrow(() -> new AssertionError("No Curve Point found"));

        assertEquals(curveId, curvePointUpdated.getCurveId());
        assertEquals(term, curvePointUpdated.getTerm());
        assertEquals(value, curvePointUpdated.getValue());
        assertEquals(curvePoint.getCreationDate(), curvePointUpdated.getCreationDate());

    }


    /**
     * Test method : delete
     * Given: valid id
     * When: GET /curvePoint/delete/{id}
     * Then: CurvePoint is deleted and redirect to /curvePoint/list
     *
     * @throws Exception exception
     */
    @Order(3)
    @Test
    public void givenValidId_whenDeleteCurvePoint_thenCurvePointIsDeletedAndRedirect() throws Exception {

        // Given
        CurvePoint curvePoint = getLastCurvePoint();
        if (curvePoint == null) {
            throw new AssertionError("No Curve Point found");
        }

        // When
        ResultActions resultActions = mockMvc.perform(
                get("/curvePoint/delete/" + curvePoint.getId())
        );

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        Optional<CurvePoint> curvePointDeleted = curvePointRepository.findById(curvePoint.getId());
        assertEquals(Optional.empty(), curvePointDeleted);
    }
}
