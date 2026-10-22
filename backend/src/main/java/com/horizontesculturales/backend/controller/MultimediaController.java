package com.horizontesculturales.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.dto.MultimediaRequest;
import com.horizontesculturales.backend.dto.MultimediaResponse;
import com.horizontesculturales.backend.service.MultimediaService;

import jakarta.validation.Valid;

@RestController
public class MultimediaController {

    private final MultimediaService multimediaService;

    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    @PostMapping("/eventos/{eventoId}/multimedia")
    @ResponseStatus(HttpStatus.CREATED)
    public MultimediaResponse agregarAEvento(@PathVariable Long eventoId, @Valid @RequestBody MultimediaRequest request) {
        return MultimediaResponse.desde(multimediaService.agregarAEvento(eventoId, request));
    }

    @DeleteMapping("/multimedia/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        multimediaService.eliminar(id);
    }
}