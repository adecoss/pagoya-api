package com.alvaro.pagoya.account.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class DuplicateAccountTypeException extends BusinessRuleException {
    public DuplicateAccountTypeException() {
        super("ya tiene una cuenta de este tipo");
    }
}
