package com.horizontesculturales.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.NovedadRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Novedad;
import com.horizontesculturales.backend.repository.NovedadRepository;
import com.horizontesculturales.backend.util.Fechas;
import com.horizontesculturales.backend.util.Paginacion;
import com.horizontesculturales.backend.util.Textos;

@Service
public class NovedadService {

    private static final int LARGO_MAXIMO_BUSQUEDA = 100;
    private static final Sort MAS_RECIENTES_PRIMERO =
        Sort.by(Sort.Direction.DESC, "fecha").and(Sort.by(Sort.Direction.DESC, "id"));

    private final NovedadRepository novedadRepository;

    public NovedadService(NovedadRepository novedadRepository) {
        this.novedadRepository = novedadRepository;
    }

    public Page<Novedad> listar(String texto, int pagina, int tamano) {
        Pageable pageable = Paginacion.pedir(pagina, tamano, MAS_RECIENTES_PRIMERO);
        String busqueda = Textos.vacioANull(texto);
        if (busqueda == null) {
            return novedadRepository.findAll(pageable);
        }
        if (busqueda.length() > LARGO_MAXIMO_BUSQUEDA) {
            busqueda = busqueda.substring(0, LARGO_MAXIMO_BUSQUEDA);
        }
        return novedadRepository.buscar(busqueda, pageable);
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
        Novedad novedad = new Novedad(request.titulo(), request.contenido(), Fechas.hoy());
        novedad.setDestacado(request.destacado());
        return novedadRepository.save(novedad);
    }

    public Novedad crearParaEvento(Evento evento) {
        Novedad novedad = new Novedad(evento.getTitulo(), evento.getDescripcion(), Fechas.hoy());
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