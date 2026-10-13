package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.GrupoRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.Grupo;
import com.horizontesculturales.backend.model.Taller;
import com.horizontesculturales.backend.repository.GrupoRepository;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final TallerService tallerService;

    public GrupoService(GrupoRepository grupoRepository, TallerService tallerService) {
        this.grupoRepository = grupoRepository;
        this.tallerService = tallerService;
    }

    public List<Grupo> obtenerPorTaller(Taller taller) {
        return grupoRepository.findByTaller(taller);
    }

    public Grupo obtenerPorId(Long id) {
        return grupoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Grupo", id));
    }

    public Grupo crear(GrupoRequest request) {
        Taller taller = tallerService.obtenerPorId(request.tallerId());
        Grupo grupo = new Grupo(request.dia(), request.horario(), request.modalidad(), taller);
        return grupoRepository.save(grupo);
    }

    public Grupo actualizar(Long id, GrupoRequest request) {
        Grupo grupo = obtenerPorId(id);
        Taller taller = tallerService.obtenerPorId(request.tallerId());
        grupo.setDia(request.dia());
        grupo.setHorario(request.horario());
        grupo.setModalidad(request.modalidad());
        grupo.setTaller(taller);
        return grupoRepository.save(grupo);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        grupoRepository.deleteById(id);
    }
}