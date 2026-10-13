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
import jakarta.validation.Valid;

import com.horizontesculturales.backend.dto.GrupoRequest;
import com.horizontesculturales.backend.dto.GrupoResponse;
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
    public List<GrupoResponse> obtenerPorTaller(@RequestParam Long tallerId) {
        Taller taller = tallerService.obtenerPorId(tallerId);
        return grupoService.obtenerPorTaller(taller).stream()
            .map(GrupoResponse::desde)
            .toList();
    }

    @GetMapping("/{id}")
    public GrupoResponse obtenerPorId(@PathVariable Long id) {
        return GrupoResponse.desde(grupoService.obtenerPorId(id));
    }

        @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoResponse crear(@Valid @RequestBody GrupoRequest request) {
        return GrupoResponse.desde(grupoService.crear(request));
    }

    @PutMapping("/{id}")
    public GrupoResponse actualizar(@PathVariable Long id, @Valid @RequestBody GrupoRequest request) {
        return GrupoResponse.desde(grupoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        grupoService.eliminar(id);
    }
}