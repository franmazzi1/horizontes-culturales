package com.horizontesculturales.backend.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TallerRequest(

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    @NotBlank(message = "La descripción es obligatoria")
    String descripcion,

    @NotEmpty(message = "Tenés que elegir al menos un profesor")
    Set<Long> profesorIds
) {}