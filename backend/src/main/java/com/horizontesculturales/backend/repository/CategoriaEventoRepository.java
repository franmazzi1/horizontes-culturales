package com.horizontesculturales.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.horizontesculturales.backend.model.CategoriaEvento;

public interface CategoriaEventoRepository extends JpaRepository<CategoriaEvento, Long> {
}
