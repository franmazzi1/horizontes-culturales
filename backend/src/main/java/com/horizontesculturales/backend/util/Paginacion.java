package com.horizontesculturales.backend.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class Paginacion {

    private static final int TAMANO_MAXIMO = 50;

    private Paginacion() {
    }

    public static Pageable pedir(int pagina, int tamano, Sort orden) {
        int paginaSegura = Math.max(pagina, 0);
        int tamanoSeguro = Math.min(Math.max(tamano, 1), TAMANO_MAXIMO);
        return PageRequest.of(paginaSegura, tamanoSeguro, orden);
    }
}