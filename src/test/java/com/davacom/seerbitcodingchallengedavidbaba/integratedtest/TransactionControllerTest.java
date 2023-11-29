package com.davacom.seerbitcodingchallengedavidbaba.integratedtest;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import com.davacom.seerbitcodingchallengedavidbaba.controller.TransactionController;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequest;
import com.davacom.seerbitcodingchallengedavidbaba.entities.TransactionStatistics;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testAddTransaction_Valid() throws Exception {
        TransactionRequest request = new TransactionRequest("100.00", Instant.now().toString());
        String requestBody = "{\"amount\":\"100.00\",\"timestamp\":\"" + Instant.now().toString() + "\"}";

        doReturn(true).when(transactionService).addTransaction(any(TransactionRequest.class));

        mockMvc.perform(post("/transaction")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddTransaction_Invalid() throws Exception {
        String requestBody = "{\"amount\":\"invalid_amount\",\"timestamp\":\"" + Instant.now().toString() + "\"}";

        doReturn(false).when(transactionService).addTransaction(any(TransactionRequest.class));

        mockMvc.perform(post("/transaction")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetStatistics() throws Exception {
        // Mocking TransactionStatistics for test purposes
        TransactionStatistics mockStatistics = new TransactionStatistics(BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(200.00), BigDecimal.valueOf(10.00), 5);

        doReturn(mockStatistics).when(transactionService).getStatistics();

        mockMvc.perform(get("/transaction/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Add assertions for the expected statistics response
                .andExpect(jsonPath("$.sum").value("100.0"))
                .andExpect(jsonPath("$.avg").value("50.0"))
                .andExpect(jsonPath("$.max").value("200.0"))
                .andExpect(jsonPath("$.min").value("10.0"))
                .andExpect(jsonPath("$.count").value("5"));
    }

    @Test
    public void testDeleteTransactions() throws Exception {
        mockMvc.perform(delete("/transaction"))
                .andExpect(status().isNoContent());
    }
}
