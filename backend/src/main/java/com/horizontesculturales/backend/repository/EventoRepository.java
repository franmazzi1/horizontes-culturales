package com.horizontesculturales.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Persona;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByDestacadoTrue();
    List<Evento> findByFechaAfter(LocalDate fecha);
    List<Evento> findByFechaBefore(LocalDate fecha);
    List<Evento> findAllByOrderByFechaDesc();
    boolean existsByExpositor(Persona expositor);

}
