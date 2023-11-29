package com.davacom.seerbitcodingchallengedavidbaba.Service;

import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequestDto;
import com.davacom.seerbitcodingchallengedavidbaba.helperMethods.TransactionStatisticsGenerator;
import org.springframework.http.ResponseEntity;


public interface TransactionService {

    ResponseEntity<Void> processTransaction(TransactionRequestDto request);

    TransactionStatisticsGenerator getTransactionStatistics();

    ResponseEntity<Void> deleteAllTransactions();
}










