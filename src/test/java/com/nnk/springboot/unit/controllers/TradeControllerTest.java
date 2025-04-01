package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.impl.TradeService;
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
 * Unit test class for the TradeControllerTest class.
 * Without security configuration
 */
@WebMvcTest(controllers = TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    /**
     * Test method : home
     * When: GET /trade/list
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenHome_thenReturnPage() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());
    }

    /**
     * Test method : addTrade
     * When: GET /trade/add
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void whenAddTrade_thenReturnPage() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }


    /**
     * Test method : validate
     * Given: form with valid data
     * When: POST /trade/validate
     * Then: Trade is created and redirect to /trade/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenValidate_thenTradeIsCreatedAndRedirect() throws Exception {

        // Given
        TradeDto tradeDto = new TradeDto();
        tradeDto.setBuyQuantity(1d);
        tradeDto.setType("type");
        tradeDto.setAccount("account");

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/validate")
                        .with(csrf().asHeader())
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", tradeDto.getBuyQuantity().toString()));

        // Then
        verify(tradeService, times(1)).create(tradeDto);
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/trade/list"));
    }

    /**
     * Test method : validate
     * Given: form with invalid data
     * When: POST /trade/validate
     * Then: Trade is not created and return the page with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenValidate_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/validate")
                        .with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""));


        // Then
        verify(tradeService, times(0)).create(any(TradeDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : showUpdateForm
     * Given: valid id
     * When: GET /trade/update/1
     * Then: Return the page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenShowUpdateForm_thenReturnPage() throws Exception {

        // Given
        when(tradeService.findById(any(Integer.class))).thenReturn(new TradeDto());

        // When
        ResultActions resultActions = mockMvc.perform(get("/trade/update/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

    }


    /**
     * Test method : showUpdateForm
     * Given: invalid id
     * When: GET /trade/update/1
     * Then: Redirect to /trade/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenShowUpdateForm_thenRedirectWithError() throws Exception {

        // Given
        when(tradeService.findById(any(Integer.class))).thenThrow(new EntityMissingException("Trade not found with id : 1"));

        // When
        ResultActions resultActions = mockMvc.perform(get("/trade/update/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }


    /**
     * Test method : updateTrade
     * Given: form with valid data
     * When: POST /trade/update/1
     * Then: Trade is updated and redirect to /trade/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidForm_whenUpdateTrade_thenTradeIsUpdatedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/update/1")
                        .with(csrf().asHeader())
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "1"));

        // Then
        verify(tradeService, times(1)).update(any(TradeDto.class));
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    /**
     * Test method : updateTrade
     * Given: form with invalid data
     * When: POST /trade/update/1
     * Then: Redirect to /trade/update with errors
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidForm_whenUpdateTrade_thenRedirectWithErrors() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/update/1")
                        .with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""));

        // Then
        verify(tradeService, times(0)).update(any(TradeDto.class));
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().hasErrors());
    }

    /**
     * Test method : updateTrade
     * Given: valid form and invalid trade id
     * When: POST /trade/update/1
     * Then: Redirect to /trade/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidFormInvalidTradeId_whenUpdateTrade_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Trade not found with id : 1")).when(tradeService).update(any(TradeDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/update/1")
                        .with(csrf().asHeader())
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(flash().attributeExists("flashMessage"));
    }

    /**
     * Test method : deleteTrade
     * Given: valid id
     * When: GET /trade/delete/1
     * Then: Trade is deleted and redirect to /trade/list
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidId_whenDeleteTrade_thenTradeIsDeletedAndRedirect() throws Exception {

        // When
        ResultActions resultActions = mockMvc.perform(get("/trade/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

    }

    /**
     * Test method : deleteTrade
     * Given: invalid id
     * When: GET /trade/delete/1
     * Then: Redirect to /trade/list with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidId_whenDeleteTrade_thenRedirectWithError() throws Exception {

        // Given
        doThrow(new EntityMissingException("Trade not found with id : 1")).when(tradeService).delete(any(Integer.class));

        // When
        ResultActions resultActions = mockMvc.perform(get("/trade/delete/1"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

}
