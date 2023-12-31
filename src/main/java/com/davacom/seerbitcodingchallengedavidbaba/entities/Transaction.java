package com.davacom.seerbitcodingchallengedavidbaba.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    private BigDecimal amount;
    private LocalDateTime timestamp;
}

