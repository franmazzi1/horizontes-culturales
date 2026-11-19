package com.horizontesculturales.backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.horizontesculturales.backend.dto.CronicaRequest;
import com.horizontesculturales.backend.dto.EventoRequest;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.CategoriaEvento;
import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.repository.EventoRepository;
import com.horizontesculturales.backend.util.Fechas;
import com.horizontesculturales.backend.util.Paginacion;

@Service
public class EventoService {

    private static final Sort MAS_RECIENTES_PRIMERO =
        Sort.by(Sort.Direction.DESC, "fecha").and(Sort.by(Sort.Direction.DESC, "id"));

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

    public Page<Evento> obtenerTodos(Long categoriaId, int pagina, int tamano) {
        Pageable pageable = Paginacion.pedir(pagina, tamano, MAS_RECIENTES_PRIMERO);
        if (categoriaId == null) {
            return eventoRepository.findAll(pageable);
        }
        return eventoRepository.findByCategoriaEventoId(categoriaId, pageable);
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

    public List<Evento> obtenerProximos(Long categoriaId) {
        if (categoriaId == null) {
            return eventoRepository.findByFechaGreaterThanEqualOrderByFechaAsc(Fechas.hoy());
        }
        return eventoRepository.findByFechaGreaterThanEqualAndCategoriaEventoIdOrderByFechaAsc(
            Fechas.hoy(), categoriaId);
    }

    public Page<Evento> obtenerRealizados(Long categoriaId, int pagina, int tamano) {
        Pageable pageable = Paginacion.pedir(pagina, tamano, MAS_RECIENTES_PRIMERO);
        if (categoriaId == null) {
            return eventoRepository.findByFechaBefore(Fechas.hoy(), pageable);
        }
        return eventoRepository.findByFechaBeforeAndCategoriaEventoId(Fechas.hoy(), categoriaId, pageable);
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

    public Evento actualizarCronica(Long id, CronicaRequest request) {
        Evento evento = obtenerPorId(id);
        evento.setCronica(request.cronica());
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