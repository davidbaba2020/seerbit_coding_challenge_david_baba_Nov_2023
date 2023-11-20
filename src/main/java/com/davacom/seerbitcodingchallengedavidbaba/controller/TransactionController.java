package com.davacom.seerbitcodingchallengedavidbaba.controller;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequestDto;
import com.davacom.seerbitcodingchallengedavidbaba.dto.responses.TransactionStatisticsDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequestDto request) {
        return transactionService.processTransaction(request);
    }

    @GetMapping("/statistics")
    public ResponseEntity<TransactionStatisticsDto> getTransactionStatistics() {
        TransactionStatisticsDto statistics = transactionService.getTransactionStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactions() {
        return transactionService.deleteAllTransactions();
    }
}