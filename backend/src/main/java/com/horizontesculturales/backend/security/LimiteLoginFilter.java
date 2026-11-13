package com.horizontesculturales.backend.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LimiteLoginFilter extends OncePerRequestFilter {

    private final IntentosLogin intentos;

    public LimiteLoginFilter(IntentosLogin intentos) {
        this.intentos = intentos;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean esLogin = "POST".equals(request.getMethod())
            && "/auth/login".equals(request.getRequestURI());
        return !esLogin;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain cadena) throws ServletException, IOException {
        long segundos = intentos.segundosRestantes(request.getParameter("email"));
        if (segundos > 0) {
            long minutos = (segundos + 59) / 60;
            response.setHeader("Retry-After", String.valueOf(segundos));
            RespuestaError.escribir(response, HttpStatus.TOO_MANY_REQUESTS,
                "Demasiados intentos fallidos. Probá de nuevo en " + minutos
                    + (minutos == 1 ? " minuto." : " minutos."));
            return;
        }
        cadena.doFilter(request, response);
    }
}