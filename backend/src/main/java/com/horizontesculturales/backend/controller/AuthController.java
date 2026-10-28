package com.horizontesculturales.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.dto.CambioPasswordRequest;
import com.horizontesculturales.backend.dto.UsuarioActualResponse;
import com.horizontesculturales.backend.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public UsuarioActualResponse usuarioActual(Authentication authentication) {
        String rol = authentication.getAuthorities().stream()
            .findFirst()
            .map(autoridad -> autoridad.getAuthority().replace("ROLE_", ""))
            .orElse("");
        return new UsuarioActualResponse(authentication.getName(), rol);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarPassword(Authentication authentication,
                                @Valid @RequestBody CambioPasswordRequest request) {
        usuarioService.cambiarPassword(authentication.getName(), request);
    }
}