package com.davacom.seerbitcodingchallengedavidbaba.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatisticsDto {
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal average= BigDecimal.ZERO;
    private BigDecimal max= BigDecimal.ZERO;
    private BigDecimal min= BigDecimal.ZERO;
    private long count;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionStatisticsDto that = (TransactionStatisticsDto) o;
        return count == that.count &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(average, that.average) &&
                Objects.equals(max, that.max) &&
                Objects.equals(min, that.min);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, average, max, min, count);
    }
}

