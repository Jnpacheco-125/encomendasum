package com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para atualizar os dados de um morador")
public record AtualizarMoradorDTO(

        @Schema(description = "Novo nome completo do morador", example = "Laura Mendes", nullable = true)
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @Schema(description = "Novo número de telefone do morador", example = "86999887766", nullable = true)
        @Pattern(regexp = "^[0-9]+$", message = "O telefone deve conter apenas números")
        String telefone,

        @Schema(description = "Novo número do apartamento do morador", example = "301", nullable = true)
        String apartamento,

        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "Novo endereço de e-mail do morador", example = "laura.mendes@email.com", format = "email", nullable = true)
        String email
) {
}
