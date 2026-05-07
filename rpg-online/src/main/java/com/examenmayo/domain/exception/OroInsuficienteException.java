package com.examenmayo.domain.exception;

public class OroInsuficienteException extends BusinessException {
    public OroInsuficienteException() {
        super("Oro insuficiente para realizar la transacción");
    }
}
