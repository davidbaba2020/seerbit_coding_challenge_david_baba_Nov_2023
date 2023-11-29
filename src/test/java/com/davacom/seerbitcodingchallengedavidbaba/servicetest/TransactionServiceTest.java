package com.davacom.seerbitcodingchallengedavidbaba.servicetest;

import com.davacom.seerbitcodingchallengedavidbaba.Service.serviceImpl.TransactionServiceImpl;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    private TransactionServiceImpl transactionService = new TransactionServiceImpl();

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl();
    }

    @Test
    public void testAddTransaction_Valid() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant timestamp = localDateTime.toInstant(ZoneOffset.UTC);

        TransactionRequest request = new TransactionRequest("100.50", timestamp.toString());
        boolean result = transactionService.addTransaction(request);
        assertTrue(result);
    }

    @Test
    public void testAddTransaction_InvalidAmount() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant timestamp = localDateTime.toInstant(ZoneOffset.UTC);

        TransactionRequest request = new TransactionRequest("invalid_amount", timestamp.toString());
        boolean result = transactionService.addTransaction(request);
        assertFalse(result);
    }

    @Test
    public void testAddTransaction_InvalidTimestamp() {
        Instant currentInstant = Instant.now();
        Instant olderInstant = currentInstant.minusSeconds(35); // Creating an older timestamp

        TransactionRequest request = new TransactionRequest("50.00", olderInstant.toString());
        boolean result = transactionService.addTransaction(request);
        assertFalse(result);
    }





}
