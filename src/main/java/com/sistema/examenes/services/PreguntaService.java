package com.sistema.examenes.services;

import com.sistema.examenes.model.Examen;
import com.sistema.examenes.model.Pregunta;

import java.util.Set;

public interface PreguntaService {

    Pregunta agregarPregunta(Pregunta pregunta);
    Pregunta actualizarPregunta(Pregunta pregunta);
    Set<Pregunta> obtenerPreguntas();
    Pregunta obtenerPregunta(Long preguntaId);
    Set<Pregunta> obtenerPreguntasDelExamen(Examen examen);
    void eliminarPregunta(Long preguntaId);
}
