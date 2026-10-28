package com.horizontesculturales.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.CambioPasswordRequest;
import com.horizontesculturales.backend.exception.EntradaInvalidaException;
import com.horizontesculturales.backend.model.Usuario;
import com.horizontesculturales.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void cambiarPassword(String email, CambioPasswordRequest request) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new IllegalStateException("El usuario autenticado no existe en la base"));

        if (!passwordEncoder.matches(request.passwordActual(), usuario.getPasswordHash())) {
            throw new EntradaInvalidaException("passwordActual", "La contraseña actual no es correcta");
        }
        if (passwordEncoder.matches(request.passwordNueva(), usuario.getPasswordHash())) {
            throw new EntradaInvalidaException("passwordNueva", "La contraseña nueva tiene que ser distinta de la actual");
        }

        usuario.setPasswordHash(passwordEncoder.encode(request.passwordNueva()));
        usuarioRepository.save(usuario);
    }
}
