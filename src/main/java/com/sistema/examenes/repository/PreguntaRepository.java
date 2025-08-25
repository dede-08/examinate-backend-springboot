package com.sistema.examenes.repository;

import com.sistema.examenes.model.Examen;
import com.sistema.examenes.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    Set<Pregunta> findByExamen(Examen examen);
}
