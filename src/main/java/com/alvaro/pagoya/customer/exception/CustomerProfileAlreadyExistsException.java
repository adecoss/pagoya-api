package com.alvaro.pagoya.customer.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class CustomerProfileAlreadyExistsException extends BusinessRuleException {
    public CustomerProfileAlreadyExistsException() {
        super("el usuario ya tiene un perfil de cliente");
    }
}
