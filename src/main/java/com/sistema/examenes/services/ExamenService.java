package com.sistema.examenes.services;

import com.sistema.examenes.model.Examen;
import com.sistema.examenes.model.Pregunta;

import java.util.Set;

public interface ExamenService {

    Examen addExamen(Examen examen);
    Examen actualizarExamen(Examen examen);
    Set<Examen> obtenerExamenes();
    Examen obtenerExamen(Long examenId);
    void deleteExamen(Long examenId);
}
