package com.davacom.seerbitcodingchallengedavidbaba.Service;

import com.davacom.seerbitcodingchallengedavidbaba.dto.requests.TransactionRequestDto;
import com.davacom.seerbitcodingchallengedavidbaba.dto.responses.TransactionStatisticsDto;
import org.springframework.http.ResponseEntity;


public interface TransactionService {

    ResponseEntity<Void> processTransaction(TransactionRequestDto request);

    TransactionStatisticsDto getTransactionStatistics();

    ResponseEntity<Void> deleteAllTransactions();
}










