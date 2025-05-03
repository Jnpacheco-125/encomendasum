package com.challenge.encomendas.encomendasum.usecase.auth;

import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.adapters.gateways.MoradorGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.infrastructure.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final FuncionarioGateway funcionarioGateway;
    private final MoradorGateway moradorGateway;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(FuncionarioGateway funcionarioGateway,
                       MoradorGateway moradorGateway,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       CustomUserDetailsService userDetailsService) {
        this.funcionarioGateway = funcionarioGateway;
        this.moradorGateway = moradorGateway;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public String autenticar(String email, String senha) {
        // Tenta buscar primeiro como funcionário
        Optional<Funcionario> optionalFuncionario = funcionarioGateway.findByEmail(email);
        if (optionalFuncionario.isPresent()) {
            Funcionario funcionario = optionalFuncionario.get();
            if (!passwordEncoder.matches(senha, funcionario.getSenha())) {
                throw new RuntimeException("Senha inválida para funcionário.");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return jwtUtil.generateToken(userDetails);
        }

        // Tenta buscar como morador
        Optional<Morador> optionalMorador = moradorGateway.findByEmail(email);
        if (optionalMorador.isPresent()) {
            Morador morador = optionalMorador.get();
            if (!passwordEncoder.matches(senha, morador.getSenha())) {
                throw new RuntimeException("Senha inválida para morador.");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return jwtUtil.generateToken(userDetails);
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }
}
