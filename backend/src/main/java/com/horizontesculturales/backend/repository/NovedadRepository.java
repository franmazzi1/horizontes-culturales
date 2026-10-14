package com.horizontesculturales.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.horizontesculturales.backend.model.Novedad;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {

    @Query("""
        select n from Novedad n
        where (n.evento is null and n.destacado = true)
           or n.evento.destacado = true
        order by n.fecha desc
        """)
    List<Novedad> findDestacadas();

    List<Novedad> findAllByOrderByFechaDesc();
}
