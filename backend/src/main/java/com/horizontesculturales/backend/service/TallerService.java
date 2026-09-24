package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.model.Taller;
import com.horizontesculturales.backend.repository.TallerRepository;

@Service
public class TallerService {

    private final TallerRepository tallerRepository;

    public TallerService(TallerRepository tallerRepository) {
        this.tallerRepository = tallerRepository;
    }

    public List<Taller> obtenerTodos() {
        return tallerRepository.findAll();
    }

    public Taller obtenerPorId(Long id) {
        return tallerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Taller no encontrado con id " + id));
    }

    public Taller crear(Taller taller) {
        return tallerRepository.save(taller);
    }

    public Taller actualizar(Long id, Taller datosNuevos) {
        Taller taller = obtenerPorId(id);
        taller.setNombre(datosNuevos.getNombre());
        taller.setDescripcion(datosNuevos.getDescripcion());
        taller.setProfesor(datosNuevos.getProfesor());
        return tallerRepository.save(taller);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        tallerRepository.deleteById(id);
    }
}