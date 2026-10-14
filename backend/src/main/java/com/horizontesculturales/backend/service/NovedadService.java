package com.horizontesculturales.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.NovedadRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Novedad;
import com.horizontesculturales.backend.repository.NovedadRepository;

@Service
public class NovedadService {

    private final NovedadRepository novedadRepository;

    public NovedadService(NovedadRepository novedadRepository) {
        this.novedadRepository = novedadRepository;
    }

    public List<Novedad> obtenerParaPortada() {
        List<Novedad> destacadas = novedadRepository.findDestacadas();

        if (destacadas.isEmpty()) {
            return novedadRepository.findAllByOrderByFechaDesc();
        }
        return destacadas;
    }

    public Novedad obtenerPorId(Long id) {
        return novedadRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Novedad", id));
    }

    public Novedad crear(NovedadRequest request) {
        Novedad novedad = new Novedad(request.titulo(), request.contenido(), LocalDate.now());
        novedad.setDestacado(request.destacado());
        return novedadRepository.save(novedad);
    }

    public Novedad crearParaEvento(Evento evento) {
        Novedad novedad = new Novedad(evento.getTitulo(), evento.getDescripcion(), LocalDate.now());
        novedad.setEvento(evento);
        return novedadRepository.save(novedad);
    }

    public Novedad actualizar(Long id, NovedadRequest request) {
        Novedad novedad = obtenerPorId(id);
        novedad.setTitulo(request.titulo());
        novedad.setContenido(request.contenido());
        novedad.setDestacado(request.destacado());
        return novedadRepository.save(novedad);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        novedadRepository.deleteById(id);
    }
}