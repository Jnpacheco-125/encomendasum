package com.challenge.encomendas.encomendasum.infrastructure.persistence.repositories;

import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.MoradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MoradorJpaRepository extends JpaRepository<MoradorEntity, Long> {
    Optional<MoradorEntity> findByEmail(String email);
    Optional<MoradorEntity> findByTelefone(String telefone);
    Optional<MoradorEntity> findByApartamento(String apartamento);

}
