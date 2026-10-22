package com.horizontesculturales.backend.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.EventoRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.CategoriaEvento;
import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.repository.EventoRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final NovedadService novedadService;
    private final CategoriaEventoService categoriaEventoService;
    private final PersonaService personaService;

    public EventoService(EventoRepository eventoRepository, NovedadService novedadService,
                         CategoriaEventoService categoriaEventoService, PersonaService personaService) {
        this.eventoRepository = eventoRepository;
        this.novedadService = novedadService;
        this.categoriaEventoService = categoriaEventoService;
        this.personaService = personaService;
    }

    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public Evento obtenerPorId(Long id) {
        return eventoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Evento", id));
    }

    public List<Evento> obtenerParaPortada() {
        List<Evento> destacados = eventoRepository.findByDestacadoTrue();

        if (destacados.isEmpty()) {
            return eventoRepository.findAllByOrderByFechaDesc();
        }
        return destacados;
    }

    public List<Evento> obtenerProximos() {
        return eventoRepository.findByFechaGreaterThanEqual(LocalDate.now());
    }

    public List<Evento> obtenerRealizados() {
        return eventoRepository.findByFechaBefore(LocalDate.now());
    }

    @Transactional
    public Evento crear(EventoRequest request) {
        CategoriaEvento categoria = categoriaEventoService.obtenerPorId(request.categoriaId());
        Set<Persona> expositores = obtenerExpositores(request.expositorIds());

                Evento evento = new Evento(request.titulo(), request.descripcion(), request.fecha(),
                                   request.horario(), request.lugar(), request.destacado(), categoria);
        evento.setExpositores(expositores);
        Evento eventoGuardado = eventoRepository.save(evento);

        novedadService.crearParaEvento(eventoGuardado);
        if ("FALLAR".equals(request.titulo())) {
            throw new RuntimeException("prueba de rollback");
        }

        return eventoGuardado;
    }

    public Evento actualizar(Long id, EventoRequest request) {
        Evento evento = obtenerPorId(id);
        CategoriaEvento categoria = categoriaEventoService.obtenerPorId(request.categoriaId());
        Set<Persona> expositores = obtenerExpositores(request.expositorIds());

        evento.setTitulo(request.titulo());
        evento.setDescripcion(request.descripcion());
        evento.setFecha(request.fecha());
        evento.setHorario(request.horario());
        evento.setLugar(request.lugar());
        evento.setDestacado(request.destacado());
        evento.setCategoriaEvento(categoria);
        evento.setExpositores(expositores);
        return eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        eventoRepository.deleteById(id);
    }

    private Set<Persona> obtenerExpositores(Set<Long> ids) {
        if (ids == null) {
            return new HashSet<>();
        }
        return personaService.obtenerPorIds(ids);
    }
}