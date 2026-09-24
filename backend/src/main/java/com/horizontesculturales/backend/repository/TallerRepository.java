package com.horizontesculturales.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizontesculturales.backend.model.Persona;
import com.horizontesculturales.backend.model.Taller;

public interface TallerRepository extends JpaRepository<Taller, Long> {
    boolean existsByProfesor(Persona profesor);
}
