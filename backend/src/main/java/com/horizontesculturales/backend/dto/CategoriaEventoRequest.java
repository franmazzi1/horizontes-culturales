package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.util.Textos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaEventoRequest(

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    String descripcion,

    @Size(max = 255, message = "La dirección de la imagen no puede superar los 255 caracteres")
    String imagenUrl
) {

    public CategoriaEventoRequest {
        descripcion = Textos.vacioANull(descripcion);
        imagenUrl = Textos.vacioANull(imagenUrl);
    }
}