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

import com.horizontesculturales.backend.dto.CronicaRequest;
import com.horizontesculturales.backend.dto.EventoRequest;
import com.horizontesculturales.backend.dto.EventoResponse;
import com.horizontesculturales.backend.dto.PaginaResponse;
import com.horizontesculturales.backend.service.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public PaginaResponse<EventoResponse> obtenerTodos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "12") int tamano) {
        return PaginaResponse.desde(
            eventoService.obtenerTodos(categoriaId, pagina, tamano), EventoResponse::desde);
    }

    @GetMapping("/portada")
    public List<EventoResponse> obtenerParaPortada() {
        return eventoService.obtenerParaPortada().stream().map(EventoResponse::desde).toList();
    }

    @GetMapping("/proximos")
    public List<EventoResponse> obtenerProximos(@RequestParam(required = false) Long categoriaId) {
        return eventoService.obtenerProximos(categoriaId).stream().map(EventoResponse::desde).toList();
    }

    @GetMapping("/realizados")
    public PaginaResponse<EventoResponse> obtenerRealizados(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "12") int tamano) {
        return PaginaResponse.desde(
            eventoService.obtenerRealizados(categoriaId, pagina, tamano), EventoResponse::desde);
    }

    @GetMapping("/{id}")
    public EventoResponse obtenerPorId(@PathVariable Long id) {
        return EventoResponse.desde(eventoService.obtenerPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse crear(@Valid @RequestBody EventoRequest request) {
        return EventoResponse.desde(eventoService.crear(request));
    }

    @PutMapping("/{id}")
    public EventoResponse actualizar(@PathVariable Long id, @Valid @RequestBody EventoRequest request) {
        return EventoResponse.desde(eventoService.actualizar(id, request));
    }

    @PutMapping("/{id}/cronica")
    public EventoResponse actualizarCronica(@PathVariable Long id,
                                            @Valid @RequestBody CronicaRequest request) {
        return EventoResponse.desde(eventoService.actualizarCronica(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
    }
}