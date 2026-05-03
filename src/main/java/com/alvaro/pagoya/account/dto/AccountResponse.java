package com.alvaro.pagoya.account.dto;

import com.alvaro.pagoya.account.model.AccountStatus;
import com.alvaro.pagoya.account.model.AccountType;
import java.math.BigDecimal;

public record AccountResponse(
    Long id,
    String accountNumber,
    BigDecimal balance,
    AccountStatus status,
    AccountType type,
    Long customerId
) {}
