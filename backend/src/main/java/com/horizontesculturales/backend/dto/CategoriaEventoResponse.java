package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.model.CategoriaEvento;

public record CategoriaEventoResponse(
    Long id,
    String nombre,
    String descripcion,
    String imagenUrl
) {

    public static CategoriaEventoResponse desde(CategoriaEvento categoria) {
        return new CategoriaEventoResponse(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion(),
            categoria.getImagenUrl()
        );
    }
}