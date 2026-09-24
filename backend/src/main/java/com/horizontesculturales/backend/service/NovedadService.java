package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.model.Novedad;
import com.horizontesculturales.backend.repository.NovedadRepository;

@Service
public class NovedadService {

    private final NovedadRepository novedadRepository;

    public NovedadService(NovedadRepository novedadRepository) {
        this.novedadRepository = novedadRepository;
    }

    public List<Novedad> obtenerParaPortada() {
        List<Novedad> destacados = novedadRepository.findByDestacadoTrue();

        if (destacados.isEmpty()) {
            return novedadRepository.findAllByOrderByFechaDesc();
        }
        return destacados;
    }

    public Novedad obtenerPorId(Long id) {
        return novedadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Novedad no encontrada con id " + id));
    }

    public Novedad crear(Novedad novedad) {
        return novedadRepository.save(novedad);
    }

    public Novedad actualizar(Long id, Novedad datosNuevos) {
        Novedad novedad = obtenerPorId(id);
        novedad.setContenido(datosNuevos.getContenido());
        novedad.setFecha(datosNuevos.getFecha());
        novedad.setDestacado(datosNuevos.isDestacado());
        return novedadRepository.save(novedad);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        novedadRepository.deleteById(id);
    }
}