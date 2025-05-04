package com.challenge.encomendas.encomendasum.usecase;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.CadastroMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.gateways.MoradorGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.domain.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MoradorService {
    private final MoradorGateway moradorGateway;
    private final PasswordEncoder passwordEncoder;

    public MoradorService(MoradorGateway moradorGateway, PasswordEncoder passwordEncoder) {
        this.moradorGateway = moradorGateway;
        this.passwordEncoder = passwordEncoder;
    }
    public Morador cadastrar(CadastroMoradorDTO dto) {
        Morador novoMorador = new Morador();
        novoMorador.setNome(dto.nome());
        novoMorador.setEmail(dto.email());
        novoMorador.setTelefone(dto.telefone());        // <-- estava faltando
        novoMorador.setApartamento(dto.apartamento());  // <-- estava faltando
        novoMorador.setSenha(passwordEncoder.encode(dto.senha()));
        novoMorador.adicionarRole(Role.ROLE_MORADOR);

        return moradorGateway.save(novoMorador);
    }


}
