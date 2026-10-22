package com.horizontesculturales.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horizontesculturales.backend.model.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
}
