package com.horizontesculturales.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.service.PersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public List<Persona> obtenerTodas() {
        return personaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Persona obtenerPorId(@PathVariable Long id) {
        return personaService.obtenerPorId(id);
    }

    @PostMapping
    public Persona crear(@RequestBody Persona persona) {
        return personaService.crear(persona);
    }

    @PutMapping("/{id}")
    public Persona actualizar(@PathVariable Long id, @RequestBody Persona datosNuevos) {
        return personaService.actualizar(id, datosNuevos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        personaService.eliminar(id);
    }
}