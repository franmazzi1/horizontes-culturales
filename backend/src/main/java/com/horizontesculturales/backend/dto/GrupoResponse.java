package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.model.Grupo;

public record GrupoResponse(
    Long id,
    String dia,
    String horario,
    String modalidad,
    Long tallerId,
    String tallerNombre
) {

    public static GrupoResponse desde(Grupo grupo) {
        return new GrupoResponse(
            grupo.getId(),
            grupo.getDia(),
            grupo.getHorario(),
            grupo.getModalidad(),
            grupo.getTaller().getId(),
            grupo.getTaller().getNombre()
        );
    }
}