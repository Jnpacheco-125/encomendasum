package com.challenge.encomendas.encomendasum.infrastructure.security;

import com.challenge.encomendas.encomendasum.usecase.auth.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Ignorar rotas públicas que não precisam de autenticação
        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-ui.html") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/v3/api-docs.yaml") ||
                path.startsWith("/v3/api-docs.json") ||
                path.startsWith("/api/postgres") ||
                path.equals("/api/funcionarios/login") ||
                path.equals("/api/funcionarios/cadastro") ||
                path.equals("/api/moradores/login") ||
                path.equals("/api/moradores/cadastro")) {

            filterChain.doFilter(request, response);
            return;
        }



        String jwt = getJwtFromRequest(request);
        log.info("Tentando extrair JWT do header: {}", jwt);

        if (StringUtils.hasText(jwt) && jwtUtil.isValidToken(jwt)) {
            String email = jwtUtil.getEmailFromToken(jwt);
            log.info("Token válido. Email extraído do token: {}", email);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            log.info("UserDetails carregado para o email: {}", userDetails.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Autenticação configurada no contexto de segurança para: {}", email);
        } else {
            log.warn("JWT ausente ou inválido.");
        }

        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
