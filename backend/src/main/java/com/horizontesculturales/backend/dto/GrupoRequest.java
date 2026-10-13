package com.horizontesculturales.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GrupoRequest(

    @NotBlank(message = "El día es obligatorio")
    @Size(max = 50, message = "El día no puede superar los 50 caracteres")
    String dia,

    @NotBlank(message = "El horario es obligatorio")
    @Size(max = 50, message = "El horario no puede superar los 50 caracteres")
    String horario,

    @NotBlank(message = "La modalidad es obligatoria")
    @Size(max = 50, message = "La modalidad no puede superar los 50 caracteres")
    String modalidad,

    @NotNull(message = "Tenés que elegir un taller")
    Long tallerId
) {}
