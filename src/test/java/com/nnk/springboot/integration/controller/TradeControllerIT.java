package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.integration.config.AbstractContainerDB;
import com.nnk.springboot.repositories.TradeRepository;
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
 * Integration test class for the TradeController class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin.admin", roles = "ADMIN")
public class TradeControllerIT extends AbstractContainerDB {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Get the last Trade created in the database
     * Utility method
     *
     * @return Trade or null
     */
    private Trade getLastTrade() {
        return tradeRepository.findAll(
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
     * When: POST /trade/validate
     * Then: Trade is created and redirect to /trade/list
     *
     * @throws Exception exception
     */
    @Order(1)
    @Test
    public void givenValidForm_whenValidate_thenTradeIsCreatedAndRedirect() throws Exception {

        // Given
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAccount("Account Created");
        tradeDto.setType("Type Created");
        tradeDto.setBuyQuantity(111d);


        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/validate")
                        .with(csrf().asHeader())
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", tradeDto.getBuyQuantity().toString()));

        // Then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/trade/list"));

        Trade tradeCreated = getLastTrade();
        assertNotNull(tradeCreated);
        assertEquals(tradeDto.getAccount(), tradeCreated.getAccount());
        assertEquals(tradeDto.getType(), tradeCreated.getType());
        assertEquals(tradeDto.getBuyQuantity(), tradeCreated.getBuyQuantity());
    }

    /**
     * Test method : updateTrade
     * Given: form with valid data
     * When: POST /trade/update/{id}
     * Then: Trade is updated and redirect to /trade/list
     *
     * @throws Exception exception
     */
    @Order(2)
    @Test
    public void givenValidForm_whenUpdateTrade_thenTradeUpdatedAndRedirect() throws Exception {

        // Given
        Trade trade = getLastTrade();
        if (trade == null) {
            throw new AssertionError("No Trade found");
        }

        String account = "Account Updated";
        String type = "Type Updated";
        String buyQuantity = "222";

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/trade/update/" + trade.getId())
                        .with(csrf().asHeader())
                        .param("account", account)
                        .param("type", type)
                        .param("buyQuantity", buyQuantity));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        Trade tradeUpdated = tradeRepository
                .findById(trade.getId())
                .orElseThrow(() -> new AssertionError("Trade : '" + trade.getId() + "' not found"));

        assertEquals(account, tradeUpdated.getAccount());
        assertEquals(type, tradeUpdated.getType());
        assertEquals(Double.valueOf(buyQuantity), tradeUpdated.getBuyQuantity());

    }

    /**
     * Test method : deleteTrade
     * Given: valid id
     * When: GET /trade/delete/{id}
     * Then: Trade is deleted and redirect to /trade/list
     *
     * @throws Exception exception
     */
    @Order(3)
    @Test
    public void givenValidId_whenDeleteTrade_thenTradeIsDeletedAndRedirect() throws Exception {

        // Given
        Trade trade = getLastTrade();
        if (trade == null) {
            throw new AssertionError("No Trade found");
        }

        // When
        ResultActions resultActions = mockMvc.perform(
                get("/trade/delete/" + trade.getId())
        );

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        Optional<Trade> tradeDeleted = tradeRepository.findById(trade.getId());
        assertEquals(Optional.empty(), tradeDeleted);
    }


}
