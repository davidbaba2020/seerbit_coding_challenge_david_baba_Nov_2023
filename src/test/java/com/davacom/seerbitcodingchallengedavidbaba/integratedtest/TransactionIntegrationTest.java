package com.davacom.seerbitcodingchallengedavidbaba.integratedtest;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import com.davacom.seerbitcodingchallengedavidbaba.Service.serviceImpl.TransactionServiceIml;
import com.davacom.seerbitcodingchallengedavidbaba.controller.TransactionController;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequestDto;
import com.davacom.seerbitcodingchallengedavidbaba.dto.responses.TransactionStatisticsDto;
import com.davacom.seerbitcodingchallengedavidbaba.helperMethods.TransactionStatisticsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionIntegrationTest {
    private TransactionController transactionController = new TransactionController();
    private TransactionServiceIml transactionService = new TransactionServiceIml();

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceIml();
        transactionController = new TransactionController(transactionService);
    }

    @Test
    void createTransaction_WithValidRequest_ReturnsCreatedResponse() {
        TransactionRequestDto request = new TransactionRequestDto();
        request.setAmount("10.00");
        request.setTimestamp(getCurrentTime());

        ResponseEntity<Void> response = transactionController.createTransaction(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void createTransaction_WithInvalidJSON_ReturnsBadRequestResponse() {
        // Prepare an invalid JSON request
        TransactionRequestDto request = new TransactionRequestDto();
        request.setAmount(null);
        request.setTimestamp("2023-11-30T12:00:00.000Z");

        ResponseEntity<Void> response = transactionController.createTransaction(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void createTransaction_WithInvalidFields_ReturnsUnprocessableEntityResponse() {
        // Prepare a request with an invalid timestamp
        TransactionRequestDto request = new TransactionRequestDto();
        request.setAmount("120.00");
        request.setTimestamp("Hello");

        ResponseEntity<Void> response = transactionController.createTransaction(request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


    @Test
    public void getTransactionStatistics_ReturnsOkResponseWithStatistics() {
        // Create a mock TransactionStatisticsDto object
        TransactionStatisticsGenerator expectedStatistics = new TransactionStatisticsGenerator();
        // Set expected values for the statistics object
        expectedStatistics.setSum(new BigDecimal("100.00"));
//        expectedStatistics.setAverage(new BigDecimal("50.00"));
        expectedStatistics.setMax(new BigDecimal("75.00"));
        expectedStatistics.setMin(new BigDecimal("25.00"));
        expectedStatistics.setCount(2);
        // Create a mock TransactionService
        TransactionService transactionService = mock(TransactionService.class);
        when(transactionService.getTransactionStatistics()).thenReturn(expectedStatistics);

        // Create an instance of TransactionController and inject the mock TransactionService
        TransactionController controller = new TransactionController(transactionService);

        // Call the method under test
        ResponseEntity<TransactionStatisticsGenerator> response = controller.getTransactionStatistics();

        // Verify that the mock TransactionService's getTransactionStatistics method was called
        verify(transactionService, times(1)).getTransactionStatistics();

        // Assert the response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatistics, response.getBody());
    }

    @Test
    public void deleteAllTransactions_ReturnsNoContentResponse() {
        // Create a mock transaction service
        TransactionService transactionService = mock(TransactionService.class);

        // Create an instance of the controller with the mock service
        TransactionController transactionController = new TransactionController(transactionService);

        // Set up the mock service to return the desired response
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        when(transactionService.deleteAllTransactions()).thenReturn(expectedResponse);

        // Call the deleteAllTransactions method on the controller
        ResponseEntity<Void> actualResponse = transactionController.deleteAllTransactions();

        // Assert that the actual response matches the expected response
        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
    }
    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return now.format(formatter);
    }
}


