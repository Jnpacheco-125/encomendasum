package com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas.EncomendaResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Encomenda;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.EncomendaEntity;
import org.springframework.stereotype.Component;

@Component
public class EncomendaMapper {
    public static EncomendaEntity toEntity(Encomenda encomenda) {
        if (encomenda == null) return null;

        EncomendaEntity entity = new EncomendaEntity();
        entity.setId(encomenda.getId());
        entity.setNomeDestinatario(encomenda.getNomeDestinatario());
        entity.setApartamento(encomenda.getApartamento());
        entity.setDescricao(encomenda.getDescricao());
        entity.setDataRecebimento(encomenda.getDataRecebimento());
        entity.setRetirada(encomenda.getRetirada());
        entity.setDataRetirada(encomenda.getDataRetirada());
        entity.setFuncionarioRecebimento(FuncionarioMapper.toEntity(encomenda.getFuncionarioRecebimento()));
        entity.setMoradorDestinatario(MoradorMapper.toEntity(encomenda.getMoradorDestinatario()));

        return entity;
    }

    public static Encomenda toDomain(EncomendaEntity entity) {
        if (entity == null) return null;

        Encomenda encomenda = new Encomenda(
                entity.getId(),
                entity.getNomeDestinatario(),
                entity.getApartamento(),
                entity.getDescricao(),
                entity.getDataRecebimento(),
                entity.getRetirada(),
                entity.getDataRetirada(),
                entity.getFuncionarioRecebimento() != null ? FuncionarioMapper.toDomain(entity.getFuncionarioRecebimento()) : null,
                entity.getMoradorDestinatario() != null ? MoradorMapper.toDomain(entity.getMoradorDestinatario()) : null
        );

        return encomenda;
    }



    public static EncomendaResponseDTO toResponseDTO(Encomenda encomenda) {
        if (encomenda == null) {
            return null;
        }

        return new EncomendaResponseDTO(
                encomenda.getId(),
                encomenda.getNomeDestinatario(),
                encomenda.getApartamento(),
                encomenda.getDescricao(),
                encomenda.getDataRecebimento(),
                encomenda.getRetirada(),
                encomenda.getDataRetirada(),
                FuncionarioMapper.toResponseDTO(encomenda.getFuncionarioRecebimento()),
                MoradorMapper.toResponseDTO(encomenda.getMoradorDestinatario())
        );
    }
}
