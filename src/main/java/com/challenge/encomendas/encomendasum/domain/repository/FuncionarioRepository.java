package com.challenge.encomendas.encomendasum.domain.repository;

import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioRepository {
    Optional<Funcionario> findById(Long id);
    Optional<Funcionario> findByEmail(String email);
    Funcionario save(Funcionario funcionario);
    void deleteById(Long id);
}
