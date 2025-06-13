package com.sistema.examenes.services.Impl;

import com.sistema.examenes.model.Usuario;
import com.sistema.examenes.model.UsuarioRol;
import com.sistema.examenes.repository.RolRepository;
import com.sistema.examenes.repository.UsuarioRepository;
import com.sistema.examenes.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception{
        Usuario usuarioLocal =  usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioLocal != null){
            System.out.println("Usuario existente");
            throw new Exception("El usuario ya existe");
        }
        else{
            for(UsuarioRol usuarioRol : usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioLocal;
    }

    @Override
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Long UsuarioId) {
        usuarioRepository.deleteById(UsuarioId);
    }

}
