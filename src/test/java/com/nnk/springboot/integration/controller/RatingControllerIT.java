package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.integration.config.AbstractContainerDB;
import com.nnk.springboot.repositories.RatingRepository;
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
 * Integration test class for the RatingController class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin.admin", roles = "ADMIN")
public class RatingControllerIT extends AbstractContainerDB {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Get the last Rating created in the database
     * Utility method
     *
     * @return Rating or null
     */
    private Rating getLastRating() {
        return ratingRepository.findAll(
                        PageRequest.of(0, 1,
                                Sort.by(Sort.Direction.DESC, "id"))
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Order(1)
    @Test
    public void givenValidForm_whenValidate_thenRatingIsCreatedAndRedirect() throws Exception {

        // Given
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("Fitch Rating Created");
        ratingDto.setOrderNumber(127);
        ratingDto.setSandPRating("SandPRating Created");
        ratingDto.setMoodysRating("MoodysRating Created");

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/validate")
                        .with(csrf().asHeader())
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", ratingDto.getOrderNumber().toString()));

        // Then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/rating/list"));

        Rating ratingCreated = getLastRating();
        assertNotNull(ratingCreated);
        assertEquals(ratingDto.getFitchRating(), ratingCreated.getFitchRating());
        assertEquals(ratingDto.getOrderNumber(), ratingCreated.getOrderNumber());
        assertEquals(ratingDto.getSandPRating(), ratingCreated.getSandPRating());
        assertEquals(ratingDto.getMoodysRating(), ratingCreated.getMoodysRating());
    }

    /**
     * Test method : updateRating
     * Given: form with valid data
     * When: POST /rating/update/{id}
     * Then: Rating is updated and redirect to /rating/list
     *
     * @throws Exception if an error occurs
     */
    @Order(2)
    @Test
    public void givenValidForm_whenUpdateRating_thenRatingUpdatedAndRedirect() throws Exception {

        // Given
        Rating rating = getLastRating();
        if (rating == null) {
            throw new AssertionError("No Rating found");
        }

        String fitchRating = "Fitch Rating Updated";
        Integer orderNumber = 127;
        String sandPRating = "SandPRating Updated";
        String moodysRating = "MoodysRating Updated";

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/rating/update/" + rating.getId())
                        .with(csrf().asHeader())
                        .param("moodysRating", moodysRating)
                        .param("sandPRating", sandPRating)
                        .param("fitchRating", fitchRating)
                        .param("orderNumber", orderNumber.toString()));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        Rating ratingUpdated = ratingRepository
                .findById(rating.getId())
                .orElseThrow(() -> new AssertionError("Rating : '" + rating.getId() + "' not found"));

        assertEquals(fitchRating, ratingUpdated.getFitchRating());
        assertEquals(orderNumber, ratingUpdated.getOrderNumber());
        assertEquals(sandPRating, ratingUpdated.getSandPRating());
        assertEquals(moodysRating, ratingUpdated.getMoodysRating());
    }


    /**
     * Test method : deleteRating
     * Given: id of the rating to delete
     * When: GET /rating/delete/{id}
     * Then: Rating is deleted and redirect to /rating/list
     *
     * @throws Exception if an error occurs
     */
    @Order(3)
    @Test
    public void givenValidId_whenDeleteRating_thenRatingIsDeletedAndRedirect() throws Exception {

        // Given
        Rating rating = getLastRating();
        if (rating == null) {
            throw new AssertionError("No Rating found");
        }

        // When
        ResultActions resultActions = mockMvc.perform(
                get("/rating/delete/" + rating.getId())
        );

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        Optional<Rating> ratingDeleted = ratingRepository.findById(rating.getId());
        assertEquals(Optional.empty(), ratingDeleted);
    }

}
