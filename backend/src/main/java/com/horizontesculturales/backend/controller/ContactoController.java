package com.horizontesculturales.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.dto.ContactoRequest;
import com.horizontesculturales.backend.dto.ContactoResponse;
import com.horizontesculturales.backend.service.ContactoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacto")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping
    public ContactoResponse obtener() {
        return ContactoResponse.desde(contactoService.obtener());
    }

    @PutMapping
    public ContactoResponse actualizar(@Valid @RequestBody ContactoRequest request) {
        return ContactoResponse.desde(contactoService.actualizar(request));
    }
}