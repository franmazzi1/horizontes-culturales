package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.CategoriaEventoRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
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
            .orElseThrow(() -> new RecursoNoEncontradoException("CategoriaEvento", id));
    }

    public CategoriaEvento crear(CategoriaEventoRequest request) {
        CategoriaEvento categoria = new CategoriaEvento(
            request.nombre(), request.descripcion(), request.imagenUrl());
        return categoriaEventoRepository.save(categoria);
    }

    public CategoriaEvento actualizar(Long id, CategoriaEventoRequest request) {
        CategoriaEvento categoria = obtenerPorId(id);
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        categoria.setImagenUrl(request.imagenUrl());
        return categoriaEventoRepository.save(categoria);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        categoriaEventoRepository.deleteById(id);
    }
}