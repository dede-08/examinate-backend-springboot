package com.sistema.examenes.config;


import com.sistema.examenes.services.Impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //  Excluir rutas públicas
        String requestPath = request.getServletPath();
        System.out.println("Ruta solicitada: " + requestPath);

        if (requestPath.startsWith("/generate-token") || requestPath.startsWith("/usuarios")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            System.out.println("Token recibido: " + jwtToken);

            try {
                username = this.jwtUtils.extractUsername(jwtToken);
                System.out.println("Usuario extraído del token: " + username);
            } catch (Exception e) {
                System.out.println("Error extrayendo username del token:");
                e.printStackTrace();
            }
        } else {
            System.out.println("Token inválido o no presente en el header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("Usuario cargado desde la base de datos: " + userDetails.getUsername());

            boolean tokenValido = this.jwtUtils.validateToken(jwtToken, userDetails);
            System.out.println("¿Token válido?: " + tokenValido);

            if (tokenValido) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Usuario autenticado en el contexto");
            } else {
                System.out.println("Token no válido");
            }
        }

        filterChain.doFilter(request, response);
    }
}
