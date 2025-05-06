package com.challenge.encomendas.encomendasum.adapters.gateways;

import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FuncionarioGateway {
    Funcionario save(Funcionario funcionario);
    Optional<Funcionario> findById(Long id);
    Optional<Funcionario> findByEmail(String email);
    void deleteById(Long id);
    Page<Funcionario> findAll(Pageable pageable);
}
