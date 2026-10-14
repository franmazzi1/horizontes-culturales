package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.PersonaRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.exception.ReglaDeNegocioException;
import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.repository.EventoRepository;
import com.horizontesculturales.backend.repository.PersonaRepository;
import com.horizontesculturales.backend.repository.TallerRepository;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<Persona> obtenerPorIds(Set<Long> ids) {
        return ids.stream()
            .map(this::obtenerPorId)
            .collect(Collectors.toSet());
    }

    public List<Persona> obtenerTodas() {
        return this.personaRepository.findAll();
    }

    public Persona obtenerPorId(Long id) {
        return this.personaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Persona", id));
    }

    public Persona crear(PersonaRequest request) {
        Persona persona = new Persona(request.nombre(), request.referencia());
        return this.personaRepository.save(persona);
    }

    public Persona actualizar(Long id, PersonaRequest request) {
        Persona persona = obtenerPorId(id);
        persona.setNombre(request.nombre());
        persona.setReferencia(request.referencia());
        return personaRepository.save(persona);
    }

    public void eliminar(Long id) {
        Persona persona = obtenerPorId(id);

        boolean tieneTaller = tallerRepository.existsByProfesoresContaining(persona);
        boolean tieneEvento = eventoRepository.existsByExpositoresContaining(persona);

        if (tieneTaller || tieneEvento) {
            throw new ReglaDeNegocioException("No se puede eliminar: la Persona tiene referencias activas");
        }

        personaRepository.deleteById(id);
    }
}