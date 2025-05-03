package com.challenge.encomendas.encomendasum.adapters.gateways.impl;

import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.FuncionarioEntity;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.FuncionarioMapper;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.repositories.FuncionarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FuncionarioJpaGateway implements FuncionarioGateway {
    private final FuncionarioJpaRepository funcionarioJpaRepository;

    @Autowired
    public FuncionarioJpaGateway(FuncionarioJpaRepository funcionarioJpaRepository) {
        this.funcionarioJpaRepository = funcionarioJpaRepository;
    }

    @Override
    public Funcionario save(Funcionario funcionario) {
        FuncionarioEntity entity = FuncionarioMapper.toEntity(funcionario);
        FuncionarioEntity saved = funcionarioJpaRepository.save(entity);
        return FuncionarioMapper.toDomain(saved);
    }
    @Override
    public Optional<Funcionario> findById(Long id) {
        return funcionarioJpaRepository.findById(id)
                .map(FuncionarioMapper::toDomain);
    }

    @Override
    public Optional<Funcionario> findByEmail(String email) {
        return funcionarioJpaRepository.findByEmail(email)
                .map(FuncionarioMapper::toDomain);
    }

    @Override
    public List<Funcionario> findAll() { // Certifique-se que retorna List<Funcionario> e não tem parâmetros
        return funcionarioJpaRepository.findAll()
                .stream()
                .map(FuncionarioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        funcionarioJpaRepository.deleteById(id);
    }
}
