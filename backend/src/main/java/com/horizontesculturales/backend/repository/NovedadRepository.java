package com.horizontesculturales.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.horizontesculturales.backend.model.Novedad;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {

    @Query("""
        select n from Novedad n
        left join n.evento e
        where (e is null and n.destacado = true)
           or e.destacado = true
        order by n.fecha desc
        """)
    List<Novedad> findDestacadas();

    List<Novedad> findAllByOrderByFechaDesc();

    @Query("""
        select n from Novedad n
        where lower(n.titulo) like lower(concat('%', :texto, '%'))
           or lower(n.contenido) like lower(concat('%', :texto, '%'))
        """)
    Page<Novedad> buscar(@Param("texto") String texto, Pageable pageable);
}