package com.davacom.seerbitcodingchallengedavidbaba.Service;

import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequest;
import com.davacom.seerbitcodingchallengedavidbaba.entities.TransactionStatistics;
import com.davacom.seerbitcodingchallengedavidbaba.helperMethods.TransactionStatisticsGenerator;
import org.springframework.http.ResponseEntity;


public interface TransactionService {
    boolean addTransaction(TransactionRequest transactionRequest);

    TransactionStatistics getStatistics();

    void deleteTransactions();
}










