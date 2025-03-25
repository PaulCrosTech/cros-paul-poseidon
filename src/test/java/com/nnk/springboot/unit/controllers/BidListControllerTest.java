package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.IBidListService;
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
 * Unit test class for the BidListController class.
 * Without security configuration
 */
@WebMvcTest(controllers = BidListController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IBidListService bidListService;


    /**
     * Test method : home
     * When: GET /bidList/list
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenHome_thenReturnPage() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : addBidForm
     * When: GET /bidList/add
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenAddBidForm_thenReturnPage() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /bidList/validate
     * Then: Bid is created and redirect to /bidList/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenValidate_thenBidIsCreatedAndRedirect() throws Exception {

        // Given
        BidDto bidDto = new BidDto();
        bidDto.setAccount("account");
        bidDto.setType("type");
        bidDto.setBidQuantity(1d);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/validate")
                        .with(csrf().asHeader())
                        .param("account", bidDto.getAccount())
                        .param("type", bidDto.getType())
                        .param("bidQuantity", bidDto.getBidQuantity().toString()));

        // Then
        verify(bidListService, times(1)).create(bidDto);
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/bidList/list"));

    }

    /**
     * Test method : validate
     * Given: form with invalid data
     * When: POST /bidList/validate
     * Then: Redirect to /bidList/add with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenValidate_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/validate")
                        .with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", ""));


        // Then
        verify(bidListService, times(0)).create(any(BidDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : showUpdateForm
     * Given: valid id
     * When: GET /bidList/update/{id}
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenShowUpdateForm_thenReturnPage() throws Exception {

        // Given
        when(bidListService.findById(any(Integer.class))).thenReturn(new BidDto());

        // When
        ResultActions resultActions = mockMvc.perform(get("/bidList/update/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

    }

    /**
     * Test method : showUpdateForm
     * Given: invalid id
     * When: GET /bidList/update/{id}
     * Then: Redirect to /bidList/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenShowUpdateForm_thenRedirectWithError() throws Exception {

        // Given
        when(bidListService.findById(any(Integer.class))).thenThrow(new EntityMissingException("Bid not found with id : 1"));

        // When
        ResultActions resultActions = mockMvc.perform(get("/bidList/update/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }


    /**
     * Test method : updateBid
     * Given: valid form
     * When: POST /bidList/update/{id}
     * Then: Bid is updated and redirect to /bidList/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenUpdateBid_thenBidIsUpdatedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/update/1")
                        .with(csrf().asHeader())
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "1"));

        // Then
        verify(bidListService, times(1)).update(any(BidDto.class));
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    /**
     * Test method : updateBid
     * Given: invalid form
     * When: POST /bidList/update/{id}
     * Then: Redirect to /bidList/update with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenUpdateBid_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/update/1")
                        .with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", ""));

        // Then
        verify(bidListService, times(0)).update(any(BidDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : updateBid
     * Given: valid form and invalid bid id
     * When: POST /bidList/update/{id}
     * Then: Redirect to /bidList/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormInvalidBidId_whenUpdateBid_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Bid not found with id : 1")).when(bidListService).update(any(BidDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/update/1")
                        .with(csrf().asHeader())
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : deleteBid
     * Given: valid id
     * When: GET /bidList/delete/{id}
     * Then: Bid is deleted and redirect to /bidList/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenDeleteBid_thenBidIsDeletedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(get("/bidList/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

    }

    /**
     * Test method : deleteBid
     * Given: invalid id
     * When: GET /bidList/delete/{id}
     * Then: Redirect to /bidList/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenDeleteBid_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Bid not found with id : 1")).when(bidListService).delete(any(Integer.class));

        // When
        ResultActions resultActions = mockMvc.perform(get("/bidList/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

}
