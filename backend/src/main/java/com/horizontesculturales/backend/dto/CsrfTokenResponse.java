package com.horizontesculturales.backend.dto;

public record CsrfTokenResponse(
    String headerName,
    String token
) {}