package com.challenge.encomendas.encomendasum.usecase;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.CadastroFuncionarioDTO;
import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class FuncionarioService {
    private final FuncionarioGateway funcionarioGateway;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioGateway funcionarioGateway, PasswordEncoder passwordEncoder) {
        this.funcionarioGateway = funcionarioGateway;
        this.passwordEncoder = passwordEncoder;
    }
    public Funcionario cadastrar(CadastroFuncionarioDTO dto) {
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(dto.nome());
        novoFuncionario.setEmail(dto.email());
        novoFuncionario.setSenha(passwordEncoder.encode(dto.senha()));
        novoFuncionario.adicionarRole(dto.role());

        return funcionarioGateway.save(novoFuncionario);
    }

    public Funcionario buscarPorId(Long id) {
        Optional<Funcionario> funcionario = funcionarioGateway.findById(id);
        return funcionario.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));
    }
}
