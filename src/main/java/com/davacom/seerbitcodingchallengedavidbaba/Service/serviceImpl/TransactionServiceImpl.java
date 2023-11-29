package com.davacom.seerbitcodingchallengedavidbaba.Service.serviceImpl;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequest;
import com.davacom.seerbitcodingchallengedavidbaba.entities.TransactionStatistics;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionServiceImpl implements TransactionService {

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = null;
    private int count = 0;

    private Instant lastUpdated = Instant.EPOCH;

    @Override
    public synchronized boolean addTransaction(TransactionRequest transactionRequest) {
        Instant timestamp = Instant.parse(transactionRequest.getTimestamp());
        BigDecimal amount;
        try {
            amount = new BigDecimal(transactionRequest.getAmount());
        } catch (NumberFormatException | NullPointerException e) {
            return false; // Invalid amount or null
        }

        Instant now = Instant.now();
        if (timestamp.isBefore(now.minusSeconds(30))) {
            return false; // Transaction is older than 30 seconds
        }

        updateStatistics(amount);
        lastUpdated = now;
        return true;
    }

    @Override
    public synchronized TransactionStatistics getStatistics() {
        Instant now = Instant.now();
        if (lastUpdated.isBefore(now.minusSeconds(30))) {
            return new TransactionStatistics(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0);
        }
        return new TransactionStatistics(sum, sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP),
                max, min, count);
    }


    @Override
    public synchronized void deleteTransactions() {
        sum = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = null;
        count = 0;
        lastUpdated = Instant.EPOCH;
    }
    private void updateStatistics(BigDecimal amount) {
        sum = sum.add(amount);
        if (max.compareTo(amount) < 0) {
            max = amount;
        }
        if (min == null || min.compareTo(amount) > 0) {
            min = amount;
        }
        count++;
    }
}
