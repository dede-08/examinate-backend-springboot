package com.sistema.examenes.services;

import com.sistema.examenes.model.Usuario;
import com.sistema.examenes.model.UsuarioRol;

import java.util.Set;

public interface UsuarioService {

    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;
    public Usuario obtenerUsuario(String username);
    public void eliminarUsuario(Long UsuarioId);

}
