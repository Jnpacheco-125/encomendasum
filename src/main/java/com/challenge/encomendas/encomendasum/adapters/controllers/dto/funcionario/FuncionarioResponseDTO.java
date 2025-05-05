package com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta detalhada de um funcionário")
public record FuncionarioResponseDTO(
        @Schema(description = "ID único do funcionário", example = "1")
        Long id,
        @Schema(description = "Nome completo do funcionário", example = "Carlos Pereira")
        String nome,
        @Schema(description = "Endereço de e-mail do funcionário", example = "carlos.pereira@email.com", format = "email")
        String email
) {
}