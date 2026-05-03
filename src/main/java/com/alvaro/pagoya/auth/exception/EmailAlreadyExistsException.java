package com.alvaro.pagoya.auth.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class EmailAlreadyExistsException extends BusinessRuleException {
    public EmailAlreadyExistsException() {
        super("el email ingresado ya esta registrado");
    }
}
