package com.sistema.examenes.services;

import com.sistema.examenes.model.Categoria;
import com.sistema.examenes.model.Examen;

import java.util.Set;

public interface CategoriaService {

    Categoria agregarCategoria(Categoria categoria);
    Categoria actualizarCategoria(Categoria categoria);
    Set<Categoria> obtenerCategorias();
    Categoria obtenerCategoria(Long categoriaId);
    void eliminarCategoria(Long categoriaId);

}
