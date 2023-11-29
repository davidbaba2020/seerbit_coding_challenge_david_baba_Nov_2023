package com.davacom.seerbitcodingchallengedavidbaba.Service;

import com.davacom.seerbitcodingchallengedavidbaba.dto.TransactionRequest;
import com.davacom.seerbitcodingchallengedavidbaba.entities.TransactionStatistics;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TransactionServiceTest {
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    public void createTransaction_ValidTransaction_ReturnsTrue() {
        // Test creation of a valid transaction
        TransactionRequest validRequest = new TransactionRequest("100.00", Instant.now().toString());
        assertTrue(transactionService.createTransaction(validRequest));
    }

    @Test
    public void deleteAllTransactions_ClearsTransactions() {
        // Test if deleteAllTransactions method clears transactions
        transactionService.createTransaction(new TransactionRequest("100.00", Instant.now().toString()));
        transactionService.deleteAllTransactions();
        assertTrue(transactionService.getTransactions().isEmpty());
    }

    @Test
    public void getStatistics_ReturnsValidStatistics() {
        // Test if getStatistics method returns valid statistics
        transactionService.createTransaction(new TransactionRequest("100.00", Instant.now().toString()));
        TransactionStatistics statistics = transactionService.getStatistics();
        log.info("Transaction stats: {}", statistics);
        assertNotNull(statistics);
    }

}