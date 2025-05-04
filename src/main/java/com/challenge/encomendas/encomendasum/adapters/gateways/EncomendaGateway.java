package com.challenge.encomendas.encomendasum.adapters.gateways;

import com.challenge.encomendas.encomendasum.domain.entities.Encomenda;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface EncomendaGateway {

    @Transactional
    Encomenda save(Encomenda encomenda);

    Optional<Encomenda> findById(Long id);

    // Buscar todas as encomendas ainda não retiradas
    List<Encomenda> findAllByRetiradaFalse();

    // Buscar todas encomendas  retiradas
    List<Encomenda> findAllByRetiradaTrue();

    // Buscar encomendas por ID do morador destinatário
    List<Encomenda> findByMoradorDestinatarioId(Long moradorId);

    @Transactional
    void deleteById(Long id);
}
