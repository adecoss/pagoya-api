package com.alvaro.pagoya.transfer.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class InsufficientBalanceException extends BusinessRuleException {
    public InsufficientBalanceException() { super("saldo insuficiente"); }
}
