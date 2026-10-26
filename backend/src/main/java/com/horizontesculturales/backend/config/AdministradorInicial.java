package com.horizontesculturales.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.horizontesculturales.backend.model.Rol;
import com.horizontesculturales.backend.model.Usuario;
import com.horizontesculturales.backend.repository.UsuarioRepository;

@Component
public class AdministradorInicial implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdministradorInicial.class);
    private static final int LARGO_MINIMO = 8;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String password;

    public AdministradorInicial(UsuarioRepository usuarioRepository,
                                PasswordEncoder passwordEncoder,
                                @Value("${admin.email}") String email,
                                @Value("${admin.password}") String password) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.count() > 0) {
            return;
        }
        if (email.isBlank() || password.length() < LARGO_MINIMO) {
            log.warn("No hay usuarios y faltan ADMIN_EMAIL o ADMIN_PASSWORD (minimo {} caracteres): "
                + "no se creo el administrador inicial", LARGO_MINIMO);
            return;
        }

        String emailNormalizado = email.trim().toLowerCase();
        usuarioRepository.save(new Usuario(emailNormalizado, passwordEncoder.encode(password), Rol.ADMIN));
        log.info("Administrador inicial creado: {}", emailNormalizado);
    }
}