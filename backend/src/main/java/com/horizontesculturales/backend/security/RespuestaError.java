package com.horizontesculturales.backend.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;

public final class RespuestaError {

    private RespuestaError() {
    }

    public static void escribir(HttpServletResponse response, HttpStatus estado, String detalle)
            throws IOException {
        response.setStatus(estado.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":" + estado.value()
            + ",\"title\":\"" + estado.getReasonPhrase()
            + "\",\"detail\":\"" + detalle + "\"}");
    }
}