package com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.entities.FuncionarioEntity;

public class FuncionarioMapper {

    public static FuncionarioEntity toEntity(Funcionario funcionario) {
        FuncionarioEntity entity = new FuncionarioEntity();
        entity.setId(funcionario.getId());
        entity.setNome(funcionario.getNome());
        entity.setEmail(funcionario.getEmail());
        entity.setSenha(funcionario.getSenha());
        entity.setRoles(funcionario.getRoles()); // Mapeando as roles
        return entity;
    }

    public static Funcionario toDomain(FuncionarioEntity entity) {
        Funcionario funcionario = new Funcionario(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getSenha()
        );
        funcionario.setRoles(entity.getRoles()); // Garantindo que as roles sejam carregadas
        return funcionario;
    }

    public static FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail()
                // Se quiser incluir roles no DTO, posso ajustar também
        );
    }
}
