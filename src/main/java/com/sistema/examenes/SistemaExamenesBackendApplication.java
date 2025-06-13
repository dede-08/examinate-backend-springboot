package com.sistema.examenes;

import com.sistema.examenes.model.Rol;
import com.sistema.examenes.model.Usuario;
import com.sistema.examenes.model.UsuarioRol;
import com.sistema.examenes.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SistemaExamenesBackendApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;
	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		/*Usuario usuario = new Usuario();

		usuario.setNombre("Nayeli");
		usuario.setApellido("Huamani");
		usuario.setUsername("nayi");
		usuario.setEmail("nahi210@gmail.com");
		usuario.setPassword("622005");
		usuario.setTelefono("983454313");
		usuario.setPerfil("foto.png");

		Rol rol = new Rol();
		rol.setRolId(1L);
		rol.setNombre("ADMIN");

		Set<UsuarioRol> usuarioRoles = new HashSet<UsuarioRol>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);

		Usuario UsuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
		System.out.println(UsuarioGuardado.getUsername());*/


	}
}
