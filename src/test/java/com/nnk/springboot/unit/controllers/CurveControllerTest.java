package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.ICurveService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * Unit test class for the CurveControllerTest class.
 * Without security configuration
 */
@WebMvcTest(controllers = CurveController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICurveService curveService;

    /**
     * Test method : home
     * When: GET /curvePoint/list
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenHome_thenReturnPage() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : addCurveForm
     * When: GET /curvePoint/add
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenAddCurveForm_thenReturnPage() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());
    }


    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /curvePoint/validate
     * Then: Curve is created and redirect to /curvePoint/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenValidate_thenCurveIsCreatedAndRedirect() throws Exception {

        // Given
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setTerm(1d);
        curvePointDto.setValue(1d);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .param("term", curvePointDto.getTerm().toString())
                        .param("value", curvePointDto.getValue().toString())
        );

        // Then
        verify(curveService, times(1)).create(curvePointDto);
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/curvePoint/list"));

    }


    /**
     * Test method : validate
     * Given: form with invalid data
     * When: POST /curvePoint/validate
     * Then: Return the add page with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenValidate_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .param("term", "")
                        .param("value", ""));


        // Then
        verify(curveService, times(0)).create(any(CurvePointDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors());
    }


    /**
     * Test method : showUpdateForm
     * Given: Valid Id
     * When: GET /curvePoint/update/{id}
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenShowUpdateForm_thenReturnPage() throws Exception {

        // Given
        when(curveService.findById(any(Integer.class))).thenReturn(new CurvePointDto());

        // When
        ResultActions resultActions = mockMvc.perform(get("/curvePoint/update/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

    }

    /**
     * Test method : showUpdateForm
     * Given: Invalid Id
     * When: GET /curvePoint/update/{id}
     * Then: Redirect to /curvePoint/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenShowUpdateForm_thenRedirectWithError() throws Exception {

        // Given
        when(curveService.findById(any(Integer.class))).thenThrow(new EntityMissingException("Curve Point not found with id : 1"));

        // When
        ResultActions resultActions = mockMvc.perform(get("/curvePoint/update/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : updateCurvePoint
     * Given: Valid form
     * When: POST /curvePoint/update/{id}
     * Then: CurvePoint is updated and redirect to /curvePoint/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenUpdateCurvePoint_thenCurvePointIsUpdatedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/update/1")
                        .with(csrf().asHeader())
                        .param("term", "1d")
                        .param("value", "2d"));

        // Then
        verify(curveService, times(1)).update(any(CurvePointDto.class));
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    /**
     * Test method : updateCurvePoint
     * Given: Invalid form
     * When: POST /curvePoint/update/{id}
     * Then: Return the update page with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenUpdateCurvePoint_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/update/1")
                        .with(csrf().asHeader())
                        .param("term", "")
                        .param("value", ""));

        // Then
        verify(curveService, times(0)).update(any(CurvePointDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : updateCurvePoint
     * Given: Valid form and invalid id
     * When: POST /curvePoint/update/{id}
     * Then: Redirect to /curvePoint/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormInvalidId_whenUpdateCurvePoint_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Curve Point not found with id : 1")).when(curveService).update(any(CurvePointDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/curvePoint/update/1")
                        .with(csrf().asHeader())
                        .param("term", "1d")
                        .param("value", "2d"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : deleteCurvePoint
     * Given: Valid Id
     * When: GET /curvePoint/delete/{id}
     * Then: CurvePoint is deleted and redirect to /curvePoint/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenDeleteCurvePoint_thenCurvePointIsDeletedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(get("/curvePoint/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

    }

    /**
     * Test method : deleteCurvePoint
     * Given: Invalid Id
     * When: GET /curvePoint/delete/{id}
     * Then: Redirect to /curvePoint/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenDeleteCurvePoint_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Curve point not found with id : 1")).when(curveService).delete(any(Integer.class));

        // When
        ResultActions resultActions = mockMvc.perform(get("/curvePoint/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

}