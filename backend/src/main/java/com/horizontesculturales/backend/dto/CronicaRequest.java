package com.horizontesculturales.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record CronicaRequest(

    @NotBlank(message = "La crónica no puede estar vacía")
    String cronica
) {}