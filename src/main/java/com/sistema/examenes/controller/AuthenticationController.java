package com.sistema.examenes.controller;

import com.sistema.examenes.config.JwtUtils;
import com.sistema.examenes.model.JwtRequest;
import com.sistema.examenes.model.JwtResponse;
import com.sistema.examenes.model.Usuario;
import com.sistema.examenes.services.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.sistema.examenes.exceptions.UsuarioNotFoundException;

import java.security.Principal;


@RestController
@CrossOrigin(origins = "allowedOriginPatterns")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {
        System.out.println("Inicio de generateToken");

        try {
            //validar credenciales
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());

            //obtener usuario
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            if (userDetails == null) {
                throw new UsuarioNotFoundException("Usuario no encontrado: " + jwtRequest.getUsername());
            }

            //generar token
            String token = this.jwtUtils.generateToken(userDetails);

            System.out.println("Token generado: " + token);
            return ResponseEntity.ok(new JwtResponse(token)); // devuelve un objeto JSON con el token

        } catch (BadCredentialsException e) {
            System.out.println("Credenciales inválidas");
            return ResponseEntity.status(401).body("Credenciales inválidas");

        } catch (UsuarioNotFoundException e){
            System.out.println("Usuario no encontrado: ");
            return ResponseEntity.status(404).body(e.getMessage());

        } catch (Exception e) {
            System.out.println("Error interno: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }

    private void autenticar(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException disabledException){
            throw new Exception("usuario deshabilitado" + disabledException.getMessage());
        }catch (BadCredentialsException badCredentialsException){
            throw new Exception("credenciales invalidas" + badCredentialsException.getMessage());
        }
    }


    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/actual-user")
    public Usuario obtenerUsuarioActual(Principal principal) {
        return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
    }

}
