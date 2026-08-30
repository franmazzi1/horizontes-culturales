package com.horizontesculturales.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.horizontesculturales.backend.model.Novedad;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {
    List<Novedad> findByDestacadoTrue();
    List<Novedad> findAllByOrderByFechaDesc();
}
