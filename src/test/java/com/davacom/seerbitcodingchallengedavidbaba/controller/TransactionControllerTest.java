package com.davacom.seerbitcodingchallengedavidbaba.controller;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testCreateTransaction_SuccessfulCreation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":\"100.00\", \"timestamp\":\"" + Instant.now() + "\"}"))
                .andExpect(
                        result -> {
                            int status = result.getResponse().getStatus();
                            if (status != 201 && status != 204) {
                                throw new AssertionError("Unexpected status: " + status);
                            }
                        }
                );
    }


    @Test
    void testCreateTransaction_TransactionOlderThanThirtySeconds() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":\"100.00\", \"timestamp\":\"" + Instant.now().minusSeconds(35) + "\"}"))
                .andExpect(status().isNoContent());
    }



    @Test
    void testGetStatistics_ReturnsStatistics() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/statistics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("Response content: {}", response);

        if (response == null || response.isEmpty()) {
            log.error("Response content is empty or null");
        }
    }
    @Test
    void testDeleteAllTransactions_SuccessfulDeletion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

}