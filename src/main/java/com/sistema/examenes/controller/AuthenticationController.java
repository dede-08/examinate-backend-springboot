package com.sistema.examenes.controller;


import com.sistema.examenes.config.JwtAuthenticationFilter;
import com.sistema.examenes.config.JwtUtils;
import com.sistema.examenes.model.JwtRequest;
import com.sistema.examenes.model.JwtResponse;
import com.sistema.examenes.services.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
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
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (BadCredentialsException e) {
            System.out.println("Credenciales inválidas");
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            System.out.println("Error al autenticar: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno del servidor");
        }


//        try {
//            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
//        }catch (Exception exception){
//            exception.printStackTrace();
//            throw new Exception("Usuario no encontrado");
//        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        System.out.println("Token generado: " + token);
        return ResponseEntity.ok(new JwtResponse(token));
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

}
