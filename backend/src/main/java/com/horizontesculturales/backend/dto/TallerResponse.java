package com.horizontesculturales.backend.dto;

import java.util.Comparator;
import java.util.List;

import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.model.Taller;

public record TallerResponse(
    Long id,
    String nombre,
    String descripcion,
    List<PersonaResponse> profesores
) {

    public static TallerResponse desde(Taller taller) {
        List<PersonaResponse> profesores = taller.getProfesores().stream()
            .sorted(Comparator.comparing(Persona::getNombre))
            .map(PersonaResponse::desde)
            .toList();

        return new TallerResponse(
            taller.getId(),
            taller.getNombre(),
            taller.getDescripcion(),
            profesores
        );
    }
}