package com.davacom.seerbitcodingchallengedavidbaba.Service.serviceImpl;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequestDto;
import com.davacom.seerbitcodingchallengedavidbaba.entities.Transaction;
import com.davacom.seerbitcodingchallengedavidbaba.helperMethods.TransactionStatisticsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
public class TransactionServiceIml implements TransactionService {

    private TransactionStatisticsGenerator transactionStatisticsCalculator = new TransactionStatisticsGenerator();
    @Override
    public synchronized ResponseEntity<Void> processTransaction(TransactionRequestDto request) {
        // Check if the request or its properties are null
        if (request == null || request.getAmount() == null || request.getTimestamp() == null) {
            return ResponseEntity.badRequest().build();
        }

        BigDecimal amount;
        LocalDateTime timestamp;

        try {
            // Parse the amount and timestamp from the request
            amount = new BigDecimal(request.getAmount());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            timestamp = LocalDateTime.parse(request.getTimestamp(), formatter);

            LocalDateTime currentTimestamp = LocalDateTime.now(ZoneOffset.UTC);
            // Check if the timestamp is in the future
            if (timestamp.isAfter(currentTimestamp)) {
                return ResponseEntity.unprocessableEntity().build();
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        LocalDateTime currentTimestamp = LocalDateTime.now(ZoneOffset.UTC);
        log.info("The current time,{} ",currentTimestamp);

        // Checks if the transaction timestamp is older than 30 seconds
        if (timestamp.isBefore(currentTimestamp.minusSeconds(30))) {
            log.info("{}, time is before 30 sec ago",timestamp);
            return ResponseEntity.noContent().build();
        }

        // Create a new transaction and add it to the transaction statistics calculator
        Transaction transaction = new Transaction(amount, timestamp);
        transactionStatisticsCalculator.addTransaction(transaction);
        log.info("The transactions count,{} ",transactionStatisticsCalculator.getCount());

        // Return a success response with the created status
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Override
    public synchronized TransactionStatisticsGenerator getTransactionStatistics() {
        //Getting each processed value for my statistic to be returned
        BigDecimal sum = transactionStatisticsCalculator.getSum();
        BigDecimal max = transactionStatisticsCalculator.getMax();
//                .setScale(2,RoundingMode.HALF_UP);
        BigDecimal min = transactionStatisticsCalculator.getMin();
        long count = transactionStatisticsCalculator.getCount();
        //Average computation
        BigDecimal average = (count == 0) ? BigDecimal.ZERO : sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        log.info("The stats,sum: {}, max: {}, min: {}, aver: {}", sum,max,min,average);

        return new TransactionStatisticsGenerator(sum, average, max, min, count);
    }

    @Override
    public synchronized ResponseEntity<Void> deleteAllTransactions() {
        try {
            // Create a new instance of the TransactionStatisticsGenerator to reset the transaction statistics
            transactionStatisticsCalculator = new TransactionStatisticsGenerator();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Return an error response if an exception occurs during the reset operation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}










