package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.model.Persona;

public record PersonaResponse(
    Long id,
    String nombre,
    String referencia
) {

    public static PersonaResponse desde(Persona persona) {
        return new PersonaResponse(
            persona.getId(),
            persona.getNombre(),
            persona.getReferencia()
        );
    }
}