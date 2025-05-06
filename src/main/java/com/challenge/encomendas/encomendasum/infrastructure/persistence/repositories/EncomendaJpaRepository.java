package com.challenge.encomendas.encomendasum.infrastructure.persistence.repositories;

import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.EncomendaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EncomendaJpaRepository extends JpaRepository<EncomendaEntity, Long> {


    // Buscar encomendas por morador destinatário
    List<EncomendaEntity> findByMoradorDestinatarioId(Long moradorId);

    // Buscar encomenda pelo ID


    // Buscar todas encomendas ainda não retiradas
    @Query("SELECT e FROM EncomendaEntity e "
            + "LEFT JOIN FETCH e.funcionarioRecebimento "
            + "LEFT JOIN FETCH e.moradorDestinatario "
            + "WHERE e.retirada = false")
    List<EncomendaEntity> findByRetiradaFalse();

    // Buscar todas encomendas  retiradas
    @Query("SELECT e FROM EncomendaEntity e "
            + "LEFT JOIN FETCH e.funcionarioRecebimento "
            + "LEFT JOIN FETCH e.moradorDestinatario "
            + "WHERE e.retirada = true")
    Page<EncomendaEntity> findByRetiradaTrue(Pageable pageable);

    // Buscar encomendas recebidas dentro de um intervalo de tempo
    @Query("SELECT e FROM EncomendaEntity e WHERE e.dataRecebimento BETWEEN :startDate AND :endDate")
    List<EncomendaEntity> findByDataRecebimentoBetween(@Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);

    // Deletar encomenda por ID (com garantia de transação)
    @Transactional
    void deleteById(Long id);
}