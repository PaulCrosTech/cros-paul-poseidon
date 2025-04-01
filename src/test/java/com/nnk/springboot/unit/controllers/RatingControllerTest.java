package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.impl.RatingService;
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
 * Unit test class for the RatingController class.
 * Without security configuration
 */
@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;


    /**
     * Test method : home
     * When: GET /rating/list
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenHome_thenReturnPage() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : addRatingForm
     * When: GET /rating/add
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenAddRatingForm_thenReturnPage() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /rating/validate
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenValidate_thenRatingIsCreatedAndRedirect() throws Exception {

        // Given
        RatingDto ratingDto = new RatingDto();
        ratingDto.setMoodysRating("moodysRating");
        ratingDto.setSandPRating("sandPRRating");
        ratingDto.setFitchRating("fitchRating");
        ratingDto.setOrderNumber(1);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/validate")
                        .with(csrf().asHeader())
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())));

        // Then
        verify(ratingService, times(1)).create(ratingDto);
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/rating/list"));

    }

    /**
     * Test method : validate
     * Given: form with invalid data
     * When: POST /rating/validate
     * Then: Return the page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenValidate_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/validate")
                        .with(csrf().asHeader())
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""));


        // Then
        verify(ratingService, times(0)).create(any(RatingDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : showUpdateForm
     * Given: valid id
     * When: GET /rating/update/{id}
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenShowUpdateForm_thenReturnPage() throws Exception {

        // Given
        when(ratingService.findById(any(Integer.class))).thenReturn(new RatingDto());

        // When
        ResultActions resultActions = mockMvc.perform(get("/rating/update/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

    }

    /**
     * Test method : showUpdateForm
     * Given: invalid id
     * When: GET /rating/update/{id}
     * Then: Redirect to /rating/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenShowUpdateForm_thenRedirectWithError() throws Exception {

        // Given
        when(ratingService.findById(any(Integer.class))).thenThrow(new EntityMissingException("Rating not found with id : 1"));

        // When
        ResultActions resultActions = mockMvc.perform(get("/rating/update/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : updateRating
     * Given: form with invalid data
     * When: POST /rating/update/{id}
     * Then: Return the page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenUpdateRating_thenRatingIsUpdatedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/update/1")
                        .with(csrf().asHeader())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "moodysRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "1"));

        // Then
        verify(ratingService, times(1)).update(any(RatingDto.class));
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    /**
     * Test method : updateRating
     * Given: form with invalid data
     * When: POST /rating/update/{id}
     * Then: Return the page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenUpdateRating_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/update/1")
                        .with(csrf().asHeader())
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""));

        // Then
        verify(ratingService, times(0)).update(any(RatingDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : updateRating
     * Given: valid form and invalid rating id
     * When: POST /rating/update/{id}
     * Then: Redirect to /rating/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormInvalidRatingId_whenUpdateRating_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Rating not found with id : 1")).when(ratingService).update(any(RatingDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/update/1")
                        .with(csrf().asHeader())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "moodysRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : deleteRating
     * Given: valid id
     * When: GET /rating/delete/{id}
     * Then: Redirect to /rating/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenDeleteRating_thenRatingIsDeletedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(get("/rating/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

    }

    /**
     * Test method : deleteRating
     * Given: invalid id
     * When: GET /rating/delete/{id}
     * Then: Redirect to /rating/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenDeleteRating_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Rating not found with id : 1")).when(ratingService).delete(any(Integer.class));

        // When
        ResultActions resultActions = mockMvc.perform(get("/rating/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
