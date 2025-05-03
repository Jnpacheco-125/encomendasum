package com.challenge.encomendas.encomendasum.adapters.gateways.impl;

import com.challenge.encomendas.encomendasum.adapters.gateways.MoradorGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.MoradorEntity;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.MoradorMapper;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.repositories.MoradorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MoradorJpaGateway implements MoradorGateway {

    private final MoradorJpaRepository moradorJpaRepository;

    @Autowired
    public MoradorJpaGateway(MoradorJpaRepository moradorJpaRepository) {
        this.moradorJpaRepository = moradorJpaRepository;
    }

    @Override
    public Morador save(Morador morador) {
        MoradorEntity entity = MoradorMapper.toEntity(morador);
        MoradorEntity saved = moradorJpaRepository.save(entity);
        return MoradorMapper.toDomain(saved);
    }

    @Override
    public Optional<Morador> findById(Long id) {
        return moradorJpaRepository.findById(id)
                .map(MoradorMapper::toDomain);
    }

    @Override
    public Optional<Morador> findByEmail(String email) {
        return moradorJpaRepository.findByEmail(email)
                .map(MoradorMapper::toDomain);
    }

    @Override
    public Optional<Morador> findByTelefone(String telefone) {
        return moradorJpaRepository.findByTelefone(telefone)
                .map(MoradorMapper::toDomain);
    }

    @Override
    public Optional<Morador> findByApartamento(String apartamento) {
        return moradorJpaRepository.findByApartamento(apartamento)
                .map(MoradorMapper::toDomain);
    }

    @Override
    public List<Morador> findAll() {
        return moradorJpaRepository.findAll()
                .stream()
                .map(MoradorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        moradorJpaRepository.deleteById(id);
    }
}
