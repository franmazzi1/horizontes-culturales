package com.horizontesculturales.backend.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EventoRequest(

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    String titulo,

    @NotBlank(message = "La descripción es obligatoria")
    String descripcion,

    @NotNull(message = "La fecha es obligatoria")
    LocalDate fecha,

    @NotBlank(message = "El horario es obligatorio")
    @Size(max = 50, message = "El horario no puede superar los 50 caracteres")
    String horario,

    @NotBlank(message = "El lugar es obligatorio")
    @Size(max = 255, message = "El lugar no puede superar los 255 caracteres")
    String lugar,

    boolean destacado,

    @NotNull(message = "Tenés que elegir una categoría")
    Long categoriaId,

    Set<Long> expositorIds
) {}