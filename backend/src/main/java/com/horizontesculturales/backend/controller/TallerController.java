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

import com.horizontesculturales.backend.model.Taller;
import com.horizontesculturales.backend.service.TallerService;

@RestController
@RequestMapping("/talleres")
public class TallerController {

    private final TallerService tallerService;

    public TallerController(TallerService tallerService) {
        this.tallerService = tallerService;
    }

    @GetMapping
    public List<Taller> obtenerTodos() {
        return tallerService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Taller obtenerPorId(@PathVariable Long id) {
        return tallerService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taller crear(@RequestBody Taller taller) {
        return tallerService.crear(taller);
    }

    @PutMapping("/{id}")
    public Taller actualizar(@PathVariable Long id, @RequestBody Taller datosNuevos) {
        return tallerService.actualizar(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tallerService.eliminar(id);
    }
}
