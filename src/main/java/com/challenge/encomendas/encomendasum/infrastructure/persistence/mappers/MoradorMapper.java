package com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.MoradorEntity;
import org.springframework.stereotype.Component;

@Component
public class MoradorMapper {
    public static MoradorEntity toEntity(Morador morador) {
        MoradorEntity entity = new MoradorEntity();
        entity.setId(morador.getId());
        entity.setNome(morador.getNome());
        entity.setTelefone(morador.getTelefone());
        entity.setApartamento(morador.getApartamento());
        entity.setEmail(morador.getEmail());
        entity.setSenha(morador.getSenha());
        entity.setRoles(morador.getRoles()); // ✅ mapeando roles
        return entity;
    }

    public static Morador toDomain(MoradorEntity entity) {
        Morador morador = new Morador(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getApartamento(),
                entity.getEmail(),
                entity.getSenha()
        );
        morador.setRoles(entity.getRoles()); // ✅ mapeando roles
        return morador;
    }

    public static MoradorResponseDTO toResponseDTO(Morador morador) {
        return new MoradorResponseDTO(
                morador.getId(),
                morador.getNome(),
                morador.getTelefone(),
                morador.getApartamento(),
                morador.getEmail()
                // ✅ não incluímos roles aqui por padrão — apenas se for necessário
        );
    }
}
