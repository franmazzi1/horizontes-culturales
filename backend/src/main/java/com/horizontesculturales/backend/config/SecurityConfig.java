package com.horizontesculturales.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.horizontesculturales.backend.security.IntentosLogin;
import com.horizontesculturales.backend.security.LimiteLoginFilter;
import com.horizontesculturales.backend.security.RespuestaError;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, IntentosLogin intentos) throws Exception {
        http
            // CSRF activo. Solo el login queda exento (hay una unica cuenta, ver ADR).
            .csrf(csrf -> csrf.ignoringRequestMatchers("/auth/login"))

            .addFilterBefore(new LimiteLoginFilter(intentos), UsernamePasswordAuthenticationFilter.class)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.GET,
                    "/talleres/**",
                    "/grupos/**",
                    "/eventos/**",
                    "/novedades/**",
                    "/categorias-evento/**",
                    "/actuator/health").permitAll()
                .anyRequest().hasRole("ADMIN"))

            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    intentos.registrarExito(request.getParameter("email"));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                })
                .failureHandler((request, response, exception) -> {
                    intentos.registrarFallo(request.getParameter("email"));
                    RespuestaError.escribir(response, HttpStatus.UNAUTHORIZED,
                        "Email o contraseña incorrectos");
                })
                .permitAll())

            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler((request, response, authentication) ->
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT))
                .permitAll())

            .exceptionHandling(errores -> errores
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }
}