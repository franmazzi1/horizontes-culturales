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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.model.Grupo;
import com.horizontesculturales.backend.model.Taller;
import com.horizontesculturales.backend.service.GrupoService;
import com.horizontesculturales.backend.service.TallerService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService grupoService;
    private final TallerService tallerService;

    public GrupoController(GrupoService grupoService, TallerService tallerService) {
        this.grupoService = grupoService;
        this.tallerService = tallerService;
    }

    @GetMapping
    public List<Grupo> obtenerPorTaller(@RequestParam Long tallerId) {
        Taller taller = tallerService.obtenerPorId(tallerId);
        return grupoService.obtenerPorTaller(taller);
    }

    @GetMapping("/{id}")
    public Grupo obtenerPorId(@PathVariable Long id) {
        return grupoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo crear(@RequestBody Grupo grupo) {
        return grupoService.crear(grupo);
    }

    @PutMapping("/{id}")
    public Grupo actualizar(@PathVariable Long id, @RequestBody Grupo datosNuevos) {
        return grupoService.actualizar(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        grupoService.eliminar(id);
    }
}