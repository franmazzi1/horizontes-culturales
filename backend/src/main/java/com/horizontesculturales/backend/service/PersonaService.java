package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.repository.EventoRepository;
import com.horizontesculturales.backend.repository.PersonaRepository;
import com.horizontesculturales.backend.repository.TallerRepository;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final TallerRepository tallerRepository;
    private final EventoRepository eventoRepository;

    public PersonaService(PersonaRepository personaRepository, TallerRepository tallerRepository, EventoRepository eventoRepository) {
        this.personaRepository = personaRepository;
        this.tallerRepository = tallerRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<Persona> obtenerTodas() {
        return this.personaRepository.findAll();
    }

    public Persona obtenerPorId(Long id) {
        return this.personaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con id " + id));
    }

    public Persona crear(Persona persona) {
        return this.personaRepository.save(persona);
    }

    public void eliminar(Long id) {
        Persona persona = obtenerPorId(id);

        boolean tieneTaller = tallerRepository.existsByProfesor(persona);
        boolean tieneEvento = eventoRepository.existsByExpositor(persona);

        if (tieneTaller || tieneEvento) {
            throw new RuntimeException("No se puede eliminar: la Persona tiene referencias activas");
        }   

        personaRepository.deleteById(id);
    }
    public Persona actualizar(Long id, Persona datosNuevos) {
        Persona persona = obtenerPorId(id);

        persona.setNombre(datosNuevos.getNombre());
        persona.setReferencia(datosNuevos.getReferencia());

        return personaRepository.save(persona);
    }

    
}