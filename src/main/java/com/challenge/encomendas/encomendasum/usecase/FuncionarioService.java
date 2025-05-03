package com.challenge.encomendas.encomendasum.usecase;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.CadastroFuncionarioDTO;
import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    private final FuncionarioGateway funcionarioGateway;

    public FuncionarioService(FuncionarioGateway funcionarioGateway) {
        this.funcionarioGateway = funcionarioGateway;

    }
    public Funcionario cadastrar(CadastroFuncionarioDTO dto) {
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(dto.nome());
        novoFuncionario.setEmail(dto.email());
       // novoFuncionario.setSenha(passwordEncoder.encode(dto.senha()));
        novoFuncionario.adicionarRole(dto.role());

        return funcionarioGateway.save(novoFuncionario);
    }
}
