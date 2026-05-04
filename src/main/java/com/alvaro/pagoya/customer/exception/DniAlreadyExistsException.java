package com.alvaro.pagoya.customer.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class DniAlreadyExistsException extends BusinessRuleException {
    public DniAlreadyExistsException() {
        super("el DNI ingresado ya esta registrado");
    }
}
