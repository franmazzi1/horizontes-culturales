package com.horizontesculturales.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovedadRequest(

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    String titulo,

    @NotBlank(message = "El contenido es obligatorio")
    String contenido,

    boolean destacado
) {}