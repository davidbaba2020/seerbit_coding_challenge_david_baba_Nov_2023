package com.davacom.seerbitcodingchallengedavidbaba.entities;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction(BigDecimal amount, Instant timestamp) {
}
