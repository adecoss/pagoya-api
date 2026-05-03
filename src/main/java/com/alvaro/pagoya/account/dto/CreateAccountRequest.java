package com.alvaro.pagoya.account.dto;

import com.alvaro.pagoya.account.model.AccountType;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
    @NotNull(message = "el tipo de cuenta es obligatorio")
    AccountType type,
    @NotNull(message = "el customerId es obligatorio")
    Long customerId
) {}
