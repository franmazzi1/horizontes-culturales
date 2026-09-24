package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.model.CategoriaEvento;
import com.horizontesculturales.backend.repository.CategoriaEventoRepository;

@Service
public class CategoriaEventoService {

    private final CategoriaEventoRepository categoriaEventoRepository;

    public CategoriaEventoService(CategoriaEventoRepository categoriaEventoRepository) {
        this.categoriaEventoRepository = categoriaEventoRepository;
    }

    public List<CategoriaEvento> obtenerTodas() {
        return categoriaEventoRepository.findAll();
    }

    public CategoriaEvento obtenerPorId(Long id) {
        return categoriaEventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CategoriaEvento no encontrada con id " + id));
    }

    public CategoriaEvento crear(CategoriaEvento categoria) {
        return categoriaEventoRepository.save(categoria);
    }

    public CategoriaEvento actualizar(Long id, CategoriaEvento datosNuevos) {
        CategoriaEvento categoria = obtenerPorId(id);
        categoria.setNombre(datosNuevos.getNombre());
        categoria.setDescripcion(datosNuevos.getDescripcion());
        categoria.setImagenUrl(datosNuevos.getImagenUrl());
        return categoriaEventoRepository.save(categoria);
    }

    public void eliminar(Long id) {
        obtenerPorId(id); // valida que exista
        categoriaEventoRepository.deleteById(id);
    }
}