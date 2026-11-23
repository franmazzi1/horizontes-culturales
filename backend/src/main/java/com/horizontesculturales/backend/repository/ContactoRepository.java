package com.horizontesculturales.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizontesculturales.backend.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}