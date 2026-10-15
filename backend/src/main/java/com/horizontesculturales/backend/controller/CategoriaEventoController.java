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

import com.horizontesculturales.backend.dto.CategoriaEventoRequest;
import com.horizontesculturales.backend.dto.CategoriaEventoResponse;
import com.horizontesculturales.backend.service.CategoriaEventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias-evento")
public class CategoriaEventoController {

    private final CategoriaEventoService categoriaEventoService;

    public CategoriaEventoController(CategoriaEventoService categoriaEventoService) {
        this.categoriaEventoService = categoriaEventoService;
    }

    @GetMapping
    public List<CategoriaEventoResponse> obtenerTodas() {
        return categoriaEventoService.obtenerTodas().stream()
            .map(CategoriaEventoResponse::desde)
            .toList();
    }

    @GetMapping("/{id}")
    public CategoriaEventoResponse obtenerPorId(@PathVariable Long id) {
        return CategoriaEventoResponse.desde(categoriaEventoService.obtenerPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaEventoResponse crear(@Valid @RequestBody CategoriaEventoRequest request) {
        return CategoriaEventoResponse.desde(categoriaEventoService.crear(request));
    }

    @PutMapping("/{id}")
    public CategoriaEventoResponse actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaEventoRequest request) {
        return CategoriaEventoResponse.desde(categoriaEventoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        categoriaEventoService.eliminar(id);
    }
}
