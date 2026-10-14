package com.horizontesculturales.backend.dto;

import java.time.LocalDate;

import com.horizontesculturales.backend.model.Novedad;

public record NovedadResponse(
    Long id,
    String titulo,
    String contenido,
    LocalDate fecha,
    boolean destacado,
    Long eventoId
) {

    public static NovedadResponse desde(Novedad novedad) {
        Long eventoId = novedad.getEvento() != null ? novedad.getEvento().getId() : null;

        return new NovedadResponse(
            novedad.getId(),
            novedad.getTitulo(),
            novedad.getContenido(),
            novedad.getFecha(),
            novedad.estaDestacada(),
            eventoId
        );
    }
}
