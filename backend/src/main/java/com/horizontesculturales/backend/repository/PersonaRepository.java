package com.horizontesculturales.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.horizontesculturales.backend.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}