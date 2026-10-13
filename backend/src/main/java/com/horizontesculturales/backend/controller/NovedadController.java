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

import com.horizontesculturales.backend.model.Novedad;
import com.horizontesculturales.backend.service.NovedadService;

@RestController
@RequestMapping("/novedades")
public class NovedadController {

    private final NovedadService novedadService;

    public NovedadController(NovedadService novedadService) {
        this.novedadService = novedadService;
    }

    @GetMapping("/portada")
    public List<Novedad> obtenerParaPortada() {
        return novedadService.obtenerParaPortada();
    }

    @GetMapping("/{id}")
    public Novedad obtenerPorId(@PathVariable Long id) {
        return novedadService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Novedad crear(@RequestBody Novedad novedad) {
        return novedadService.crear(novedad);
    }

    @PutMapping("/{id}")
    public Novedad actualizar(@PathVariable Long id, @RequestBody Novedad datosNuevos) {
        return novedadService.actualizar(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        novedadService.eliminar(id);
    }
}