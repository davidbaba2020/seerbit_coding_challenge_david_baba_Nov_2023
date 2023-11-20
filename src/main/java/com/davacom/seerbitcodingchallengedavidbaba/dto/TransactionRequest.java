package com.davacom.seerbitcodingchallengedavidbaba.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public
class TransactionRequest {
    @NotBlank(message = "Field cannot be blank")
    private String amount;
    @NotBlank(message = "Field cannot be blank")
    private String timestamp;
}