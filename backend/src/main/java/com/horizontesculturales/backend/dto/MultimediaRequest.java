package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.model.TipoMultimedia;
import com.horizontesculturales.backend.util.Textos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MultimediaRequest(

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 500, message = "La dirección no puede superar los 500 caracteres")
    String url,

    @NotNull(message = "Tenés que indicar si es una imagen o un video")
    TipoMultimedia tipo
) {

    public MultimediaRequest {
        url = Textos.vacioANull(url);
    }
}