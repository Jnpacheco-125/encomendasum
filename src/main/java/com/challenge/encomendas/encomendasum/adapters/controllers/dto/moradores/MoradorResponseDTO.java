package com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta detalhada de um morador")
public record MoradorResponseDTO(

        @Schema(description = "ID único do morador", example = "2")
        Long id,
        @Schema(description = "Nome completo do morador", example = "Fernanda Oliveira")
        String nome,
        @Schema(description = "Número de telefone do morador", example = "86988990011")
        String telefone,
        @Schema(description = "Número do apartamento do morador", example = "402")
        String apartamento,
        @Schema(description = "Endereço de e-mail do morador", example = "fernanda.oliveira@email.com", format = "email")
        String email
) {
}
