package com.davacom.seerbitcodingchallengedavidbaba.controller;

import com.davacom.seerbitcodingchallengedavidbaba.Service.TransactionService;
import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequest;
import com.davacom.seerbitcodingchallengedavidbaba.entities.TransactionStatistics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> addTransaction(@RequestBody TransactionRequest transactionRequest) {
        boolean isAdded = transactionService.addTransaction(transactionRequest);
        if (isAdded) {
            return ResponseEntity.status(201).build(); // Successfully added
        } else {
            return ResponseEntity.noContent().build(); // Older than 30 seconds or invalid
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<TransactionStatistics> getStatistics() {
        TransactionStatistics statistics = transactionService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.deleteTransactions();
        return ResponseEntity.noContent().build();
    }
}
