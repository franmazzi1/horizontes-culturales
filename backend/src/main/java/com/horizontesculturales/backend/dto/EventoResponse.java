package com.horizontesculturales.backend.dto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Persona;

public record EventoResponse(
    Long id,
    String titulo,
    String descripcion,
    String cronica,
    LocalDate fecha,
    String horario,
    String lugar,
    boolean destacado,
    Long categoriaId,
    String categoriaNombre,
    List<PersonaResponse> expositores
    
) {

    public static EventoResponse desde(Evento evento) {
        List<PersonaResponse> expositores = evento.getExpositores().stream()
            .sorted(Comparator.comparing(Persona::getNombre))
            .map(PersonaResponse::desde)
            .toList();

        return new EventoResponse(
            evento.getId(),
            evento.getTitulo(),
            evento.getDescripcion(),
            evento.getCronica(),
            evento.getFecha(),
            evento.getHorario(),
            evento.getLugar(),
            evento.isDestacado(),
            evento.getCategoriaEvento().getId(),
            evento.getCategoriaEvento().getNombre(),
            expositores
        );
    }
}
