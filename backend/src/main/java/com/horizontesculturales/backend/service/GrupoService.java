package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.model.Grupo;
import com.horizontesculturales.backend.model.Taller;
import com.horizontesculturales.backend.repository.GrupoRepository;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;

    public GrupoService(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public List<Grupo> obtenerPorTaller(Taller taller) {
        return grupoRepository.findByTaller(taller);
    }

    public Grupo obtenerPorId(Long id) {
        return grupoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado con id " + id));
    }

    public Grupo crear(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public Grupo actualizar(Long id, Grupo datosNuevos) {
        Grupo grupo = obtenerPorId(id);
        grupo.setDia(datosNuevos.getDia());
        grupo.setHorario(datosNuevos.getHorario());
        grupo.setModalidad(datosNuevos.getModalidad());
        return grupoRepository.save(grupo);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        grupoRepository.deleteById(id);
    }
}