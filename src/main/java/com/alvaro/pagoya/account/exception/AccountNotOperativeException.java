package com.alvaro.pagoya.account.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class AccountNotOperativeException extends BusinessRuleException {
    public AccountNotOperativeException() {
        super("la cuenta origen no esta operativa");
    }
}
