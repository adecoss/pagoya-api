package com.alvaro.pagoya.transfer.exception;

import com.alvaro.pagoya.shared.exception.BusinessRuleException;

public class SameAccountTransferException extends BusinessRuleException {
    public SameAccountTransferException() {
        super("la cuenta origen y destino no pueden ser la misma");
    }
}
