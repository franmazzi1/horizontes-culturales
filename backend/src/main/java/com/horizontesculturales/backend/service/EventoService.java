package com.horizontesculturales.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.repository.EventoRepository;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public Evento obtenerPorId(Long id) {
        return eventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado con id " + id));
    }

    public List<Evento> obtenerParaPortada() {
        List<Evento> destacados = eventoRepository.findByDestacadoTrue();

        if (destacados.isEmpty()) {
            return eventoRepository.findAllByOrderByFechaDesc();
        }
        return destacados;
    }

    public List<Evento> obtenerProximos() {
        return eventoRepository.findByFechaAfter(LocalDate.now());
    }

    public List<Evento> obtenerRealizados() {
        return eventoRepository.findByFechaBefore(LocalDate.now());
    }

    public Evento crear(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento actualizar(Long id, Evento datosNuevos) {
        Evento evento = obtenerPorId(id);
        evento.setDescripcion(datosNuevos.getDescripcion());
        evento.setFecha(datosNuevos.getFecha());
        evento.setLugar(datosNuevos.getLugar());
        evento.setDestacado(datosNuevos.isDestacado());
        evento.setCategoriaEvento(datosNuevos.getCategoriaEvento());
        evento.setExpositor(datosNuevos.getExpositor());
        return eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        eventoRepository.deleteById(id);
    }
}