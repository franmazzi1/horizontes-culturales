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

import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> obtenerTodos() {
        return eventoService.obtenerTodos();
    }

    @GetMapping("/portada")
    public List<Evento> obtenerParaPortada() {
        return eventoService.obtenerParaPortada();
    }

    @GetMapping("/proximos")
    public List<Evento> obtenerProximos() {
        return eventoService.obtenerProximos();
    }

    @GetMapping("/realizados")
    public List<Evento> obtenerRealizados() {
        return eventoService.obtenerRealizados();
    }

    @GetMapping("/{id}")
    public Evento obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento crear(@RequestBody Evento evento) {
        return eventoService.crear(evento);
    }

    @PutMapping("/{id}")
    public Evento actualizar(@PathVariable Long id, @RequestBody Evento datosNuevos) {
        return eventoService.actualizar(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
    }
}