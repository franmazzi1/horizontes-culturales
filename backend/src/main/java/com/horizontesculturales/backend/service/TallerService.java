package com.horizontesculturales.backend.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.TallerRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.model.Taller;
import com.horizontesculturales.backend.repository.TallerRepository;

@Service
public class TallerService {

    private final TallerRepository tallerRepository;
    private final PersonaService personaService;

    public TallerService(TallerRepository tallerRepository, PersonaService personaService) {
        this.tallerRepository = tallerRepository;
        this.personaService = personaService;
    }

    public List<Taller> obtenerTodos() {
        return tallerRepository.findAll();
    }

    public Taller obtenerPorId(Long id) {
        return tallerRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Taller", id));
    }

    public Taller crear(TallerRequest request) {
        Set<Persona> profesores = personaService.obtenerPorIds(request.profesorIds());
        Taller taller = new Taller(request.nombre(), request.descripcion(), profesores);
        return tallerRepository.save(taller);
    }

    public Taller actualizar(Long id, TallerRequest request) {
        Taller taller = obtenerPorId(id);
        Set<Persona> profesores = personaService.obtenerPorIds(request.profesorIds());
        taller.setNombre(request.nombre());
        taller.setDescripcion(request.descripcion());
        taller.setProfesores(profesores);
        return tallerRepository.save(taller);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        tallerRepository.deleteById(id);
    }
}