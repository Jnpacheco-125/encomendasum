package com.challenge.encomendas.encomendasum.domain.repository;

import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MoradorRepository {
    Optional<Morador> findById(Long id);
    Optional<Morador> findByEmail(String email);
    Optional<Morador> findByTelefone(String telefone);
    Optional<Morador> findByApartamento(String apartamento);
    Morador save(Morador morador);
    void deleteById(Long id);
    Page<Morador> findAll(Pageable pageable);
}
