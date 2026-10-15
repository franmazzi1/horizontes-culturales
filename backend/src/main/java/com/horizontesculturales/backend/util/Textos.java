package com.horizontesculturales.backend.util;

public final class Textos {

    private Textos() {
    }

    public static String vacioANull(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }
        return texto.trim();
    }
}