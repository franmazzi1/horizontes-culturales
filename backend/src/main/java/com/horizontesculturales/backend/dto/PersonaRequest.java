package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.util.Textos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonaRequest(

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    @Size(max = 255, message = "La referencia no puede superar los 255 caracteres")
    String referencia
) {

    public PersonaRequest {
        referencia = Textos.vacioANull(referencia);
    }
}