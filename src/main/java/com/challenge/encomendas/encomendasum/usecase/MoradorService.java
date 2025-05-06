package com.challenge.encomendas.encomendasum.usecase;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.AtualizarMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.CadastroMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.gateways.MoradorGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Morador buscarPorId(Long id) {
        Optional<Morador> morador = moradorGateway.findById(id);
        return morador.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));
    }

    public Morador buscarPorEmail(String email) {
        Optional<Morador> morador = moradorGateway.findByEmail(email);
        return morador.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));
    }

    public Morador buscarPorTelefone(String telefone) {
        Optional<Morador> morador = moradorGateway.findByTelefone(telefone);
        return morador.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));
    }

    public Morador buscarPorApartamento(String apartamento) {
        Optional<Morador> morador = moradorGateway.findByApartamento(apartamento);
        return morador.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));
    }

    public Page<Morador> buscarTodos(Pageable pageable) {
        return moradorGateway.findAll(pageable);
    }

    public void deletarMorador(Long id) {
        // Opcional: Verificar se o morador existe antes de deletar
        if (moradorGateway.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador com o ID " + id + " não encontrado.");
        }
        moradorGateway.deleteById(id);
    }

    public Morador atualizar(Long id, AtualizarMoradorDTO dto) {
        Morador morador = moradorGateway.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));

        if (dto.nome() != null && !dto.nome().isBlank()) {
            morador.setNome(dto.nome());
        }

        if (dto.telefone() != null && !dto.telefone().isBlank()) {
            morador.setTelefone(dto.telefone());
        }

        if (dto.apartamento() != null && !dto.apartamento().isBlank()) {
            morador.setApartamento(dto.apartamento());
        }

        if (dto.email() != null && !dto.email().isBlank()) {
            morador.setEmail(dto.email());
        }

        return moradorGateway.save(morador);
    }

}
