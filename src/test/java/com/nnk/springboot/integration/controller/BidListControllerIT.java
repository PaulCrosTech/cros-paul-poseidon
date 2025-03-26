package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.integration.config.AbstractContainerDB;
import com.nnk.springboot.repositories.BidListRepository;
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
 * Integration test class for the BidListController class.
 * With ADMIN role
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin.admin", roles = "ADMIN")
public class BidListControllerIT extends AbstractContainerDB {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Récupère le dernier Bid crée en base de données
     * Méthode utilitaire
     *
     * @return Bid or null
     */
    private Bid getLastBid() {
        return bidListRepository.findAll(
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
     * When: POST /bidList/validate
     * Then: Bid is created and redirect to /bidList/list
     *
     * @throws Exception if an error occurs
     */
    @Order(1)
    @Test
    public void givenValidForm_whenValidate_thenBidIsCreatedAndRedirect() throws Exception {

        // Given
        BidDto bidDto = new BidDto();
        bidDto.setAccount("Account Created");
        bidDto.setType("Type Created");
        bidDto.setBidQuantity(666d);

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/validate")
                        .with(csrf().asHeader())
                        .param("account", bidDto.getAccount())
                        .param("type", bidDto.getType())
                        .param("bidQuantity", bidDto.getBidQuantity().toString()));

        // Then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/bidList/list"));

        Bid bidCreated = getLastBid();
        assertNotNull(bidCreated);
        assertEquals(bidDto.getAccount(), bidCreated.getAccount());
        assertEquals(bidDto.getType(), bidCreated.getType());
        assertEquals(bidDto.getBidQuantity(), bidCreated.getBidQuantity());
    }

    /**
     * Test method : updateBid
     * Given: form with valid data
     * When: POST /bidList/update/{id}
     * Then: Bid is updated and redirect to /bidList/list
     *
     * @throws Exception if an error occurs
     */
    @Order(2)
    @Test
    public void givenValidForm_whenUpdateBid_thenBidUpdatedAndRedirect() throws Exception {

        // Given
        Bid bid = getLastBid();
        if (bid == null) {
            throw new AssertionError("No Bid found");
        }

        String account = "Account Updated";
        String type = "Type Updated";
        String bidQuantity = "999";

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/bidList/update/" + bid.getId())
                        .with(csrf().asHeader())
                        .param("account", account)
                        .param("type", type)
                        .param("bidQuantity", bidQuantity));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        Bid bidUpdated = bidListRepository.findById(bid.getId())
                .orElseThrow(() -> new AssertionError("Bid : '" + bid.getId() + "' not found"));

        assertEquals(account, bidUpdated.getAccount());
        assertEquals(type, bidUpdated.getType());
        assertEquals(Double.valueOf(bidQuantity), bidUpdated.getBidQuantity());

    }


    /**
     * Test method : deleteBid
     * Given: existing bid id
     * When: GET /bidList/delete/{id}
     * Then: Bid is deleted and redirect to /bidList/list
     *
     * @throws Exception if an error occurs
     */
    @Order(3)
    @Test
    public void givenValidId_whenDeleteBid_thenBidIsDeletedAndRedirect() throws Exception {

        // Given
        Bid bid = getLastBid();
        if (bid == null) {
            throw new AssertionError("No Bid found");
        }

        // When
        ResultActions resultActions = mockMvc.perform(
                get("/bidList/delete/" + bid.getId())
        );

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        Optional<Bid> bidDeleted = bidListRepository.findById(bid.getId());
        assertEquals(Optional.empty(), bidDeleted);
    }

}
