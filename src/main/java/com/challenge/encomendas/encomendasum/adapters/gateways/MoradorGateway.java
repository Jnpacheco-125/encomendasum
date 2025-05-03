package com.challenge.encomendas.encomendasum.adapters.gateways;

import com.challenge.encomendas.encomendasum.domain.entities.Morador;

import java.util.List;
import java.util.Optional;

public interface MoradorGateway {
    Morador save(Morador morador);
    Optional<Morador> findById(Long id);
    Optional<Morador> findByEmail(String email);
    Optional<Morador> findByTelefone(String telefone);
    Optional<Morador> findByApartamento(String apartamento);
    List<Morador> findAll(); // Se precisar listar todos os moradores
    void deleteById(Long id);
}

