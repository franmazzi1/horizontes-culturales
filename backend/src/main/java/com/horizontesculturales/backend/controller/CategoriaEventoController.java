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

import com.horizontesculturales.backend.model.CategoriaEvento;
import com.horizontesculturales.backend.service.CategoriaEventoService;

@RestController
@RequestMapping("/categorias-evento")
public class CategoriaEventoController {

    private final CategoriaEventoService categoriaEventoService;

    public CategoriaEventoController(CategoriaEventoService categoriaEventoService) {
        this.categoriaEventoService = categoriaEventoService;
    }

    @GetMapping
    public List<CategoriaEvento> obtenerTodas() {
        return categoriaEventoService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public CategoriaEvento obtenerPorId(@PathVariable Long id) {
        return categoriaEventoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaEvento crear(@RequestBody CategoriaEvento categoria) {
        return categoriaEventoService.crear(categoria);
    }

    @PutMapping("/{id}")
    public CategoriaEvento actualizar(@PathVariable Long id, @RequestBody CategoriaEvento datosNuevos) {
        return categoriaEventoService.actualizar(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        categoriaEventoService.eliminar(id);
    }
}
