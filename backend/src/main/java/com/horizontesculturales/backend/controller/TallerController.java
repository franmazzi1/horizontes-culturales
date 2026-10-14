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

import com.horizontesculturales.backend.dto.TallerRequest;
import com.horizontesculturales.backend.dto.TallerResponse;
import com.horizontesculturales.backend.service.TallerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/talleres")
public class TallerController {

    private final TallerService tallerService;

    public TallerController(TallerService tallerService) {
        this.tallerService = tallerService;
    }

    @GetMapping
    public List<TallerResponse> obtenerTodos() {
        return tallerService.obtenerTodos().stream()
            .map(TallerResponse::desde)
            .toList();
    }

    @GetMapping("/{id}")
    public TallerResponse obtenerPorId(@PathVariable Long id) {
        return TallerResponse.desde(tallerService.obtenerPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TallerResponse crear(@Valid @RequestBody TallerRequest request) {
        return TallerResponse.desde(tallerService.crear(request));
    }

    @PutMapping("/{id}")
    public TallerResponse actualizar(@PathVariable Long id, @Valid @RequestBody TallerRequest request) {
        return TallerResponse.desde(tallerService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tallerService.eliminar(id);
    }
}
