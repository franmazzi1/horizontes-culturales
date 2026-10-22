package com.horizontesculturales.backend.exception;

public class EntradaInvalidaException extends RuntimeException {

    private final String campo;

    public EntradaInvalidaException(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
