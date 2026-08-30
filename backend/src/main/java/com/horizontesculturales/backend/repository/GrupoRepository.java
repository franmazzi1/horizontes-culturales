package com.horizontesculturales.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.horizontesculturales.backend.model.Grupo;
import com.horizontesculturales.backend.model.Taller;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByTaller(Taller taller);
}
