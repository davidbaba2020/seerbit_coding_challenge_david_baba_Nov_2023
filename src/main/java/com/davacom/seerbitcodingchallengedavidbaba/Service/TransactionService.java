package com.davacom.seerbitcodingchallengedavidbaba.Service;

import com.davacom.seerbitcodingchallengedavidbaba.dto.TransactionRequest;
import com.davacom.seerbitcodingchallengedavidbaba.entities.TransactionStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TransactionService {
    private final static Map<Instant, BigDecimal> transactions = new ConcurrentHashMap<>();

        Instant now = Instant.now();

    public boolean createTransaction(TransactionRequest request) {
        try {
            BigDecimal amount = new BigDecimal(request.getAmount()).setScale(2, RoundingMode.HALF_UP); // Ensure two decimal places with rounding
            Instant timestamp = Instant.now();
//            Instant timestamp = Instant.parse(request.getTimestamp());

            if (isOlderThanThirtySeconds(timestamp)) {
                return false; // Transaction is older than 30 seconds
            }

            transactions.put(now.minusSeconds(10), new BigDecimal("100.00"));
            transactions.put(now.minusSeconds(15), new BigDecimal("150.00"));
            transactions.put(now.minusSeconds(25), new BigDecimal("80.00"));
            transactions.put(now.minusSeconds(5), new BigDecimal("200.00"));
            transactions.put(timestamp, amount);
            log.info("The transactions are: {}", transactions);
            System.out.println(transactions.size());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating transaction", e);
        }
    }

    private boolean isOlderThanThirtySeconds(Instant timestamp) {
        return timestamp.isBefore(Instant.now().minus(Duration.ofSeconds(30)));
    }

    public synchronized TransactionStatistics getStatistics() {
        if(transactions.isEmpty()) {
            return TransactionStatistics.builder()
                    .sum(BigDecimal.valueOf(0.0))
                    .avg(BigDecimal.valueOf(0.0))
                    .max(BigDecimal.valueOf(0.0))
                    .min(BigDecimal.valueOf(0.0))
                    .build();
        }
        Instant thirtySecondsAgo = Instant.now().minusSeconds(30);
        DoubleSummaryStatistics stats = transactions.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(thirtySecondsAgo))
                .mapToDouble(entry -> entry.getValue().doubleValue())
                .summaryStatistics();

        log.info("Transactions are: {}",transactions);
        log.info("Transactions size is: {}",transactions.size());
        log.info("Transactions sats: {}",stats);

        BigDecimal sum = BigDecimal.valueOf(stats.getSum()).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal avg = BigDecimal.valueOf(stats.getAverage()).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal max = BigDecimal.valueOf(stats.getMax()).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal min = BigDecimal.valueOf(stats.getMin()).setScale(2, BigDecimal.ROUND_HALF_UP);

        TransactionStatistics transactionStatistics = new TransactionStatistics(sum, avg, max, min, stats.getCount());
        log.info("Transactions stats are: {}", transactionStatistics );
        return transactionStatistics;
    }

    public Map<Instant, BigDecimal> getTransactions() {
        return transactions;
    }

    public synchronized void deleteAllTransactions() {
        transactions.clear();
        log.info("Size after deleting is : {}", transactions.size());
    }
}