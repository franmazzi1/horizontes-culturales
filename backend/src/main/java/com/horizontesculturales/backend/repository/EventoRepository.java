package com.horizontesculturales.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Persona;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByDestacadoTrue();
    List<Evento> findAllByOrderByFechaDesc();

    List<Evento> findByFechaGreaterThanEqualOrderByFechaAsc(LocalDate fecha);
    List<Evento> findByFechaGreaterThanEqualAndCategoriaEventoIdOrderByFechaAsc(LocalDate fecha, Long categoriaId);

    Page<Evento> findByFechaBefore(LocalDate fecha, Pageable pageable);
    Page<Evento> findByFechaBeforeAndCategoriaEventoId(LocalDate fecha, Long categoriaId, Pageable pageable);

    Page<Evento> findByCategoriaEventoId(Long categoriaId, Pageable pageable);

    boolean existsByExpositoresContaining(Persona expositor);
}