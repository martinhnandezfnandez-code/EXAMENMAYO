package com.examenmayo.domain.exception;

public class InventarioLlenoException extends BusinessException {
    public InventarioLlenoException() {
        super("El inventario está lleno");
    }
}
