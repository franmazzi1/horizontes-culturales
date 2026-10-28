package com.horizontesculturales.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.dto.UsuarioActualResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public UsuarioActualResponse usuarioActual(Authentication authentication) {
        String rol = authentication.getAuthorities().stream()
            .findFirst()
            .map(autoridad -> autoridad.getAuthority().replace("ROLE_", ""))
            .orElse("");
        return new UsuarioActualResponse(authentication.getName(), rol);
    }
}