package com.horizontesculturales.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambioPasswordRequest(

    @NotBlank(message = "Tenés que escribir tu contraseña actual")
    String passwordActual,

    @NotBlank(message = "La contraseña nueva es obligatoria")
    @Size(min = 8, max = 72, message = "La contraseña nueva tiene que tener entre 8 y 72 caracteres")
    String passwordNueva
) {

    @Override
    public String toString() {
        return "CambioPasswordRequest[passwordActual=***, passwordNueva=***]";
    }
}