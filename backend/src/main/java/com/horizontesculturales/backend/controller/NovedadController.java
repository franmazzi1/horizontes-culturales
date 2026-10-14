package com.horizontesculturales.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.dto.NovedadRequest;
import com.horizontesculturales.backend.dto.NovedadResponse;
import com.horizontesculturales.backend.service.NovedadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/novedades")
public class NovedadController {

    private final NovedadService novedadService;

    public NovedadController(NovedadService novedadService) {
        this.novedadService = novedadService;
    }

    @GetMapping("/portada")
    public List<NovedadResponse> obtenerParaPortada() {
        return novedadService.obtenerParaPortada().stream().map(NovedadResponse::desde).toList();
    }

    @GetMapping("/{id}")
    public NovedadResponse obtenerPorId(@PathVariable Long id) {
        return NovedadResponse.desde(novedadService.obtenerPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NovedadResponse crear(@Valid @RequestBody NovedadRequest request) {
        return NovedadResponse.desde(novedadService.crear(request));
    }

    @PutMapping("/{id}")
    public NovedadResponse actualizar(@PathVariable Long id, @Valid @RequestBody NovedadRequest request) {
        return NovedadResponse.desde(novedadService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        novedadService.eliminar(id);
    }
}