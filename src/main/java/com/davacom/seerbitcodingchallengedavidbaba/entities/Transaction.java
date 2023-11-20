package com.davacom.seerbitcodingchallengedavidbaba.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    private BigDecimal amount;
    private LocalDateTime timestamp;
}

