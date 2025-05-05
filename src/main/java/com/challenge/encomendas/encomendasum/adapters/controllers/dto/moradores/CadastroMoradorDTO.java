package com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores;

import com.challenge.encomendas.encomendasum.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para cadastrar um novo morador")
public record CadastroMoradorDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        @Schema(description = "Nome completo do morador", example = "Mariana Costa")
        String nome,

        @NotBlank(message = "O telefone é obrigatório")
        @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres")
        @Schema(description = "Número de telefone do morador", example = "86999775533")
        String telefone,

        @NotBlank(message = "O apartamento é obrigatório")
        @Size(max = 10, message = "O apartamento não pode ter mais de 10 caracteres")
        @Schema(description = "Número do apartamento do morador", example = "102")
        String apartamento,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "Endereço de e-mail do morador", example = "mariana.costa@email.com", format = "email")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        @Schema(description = "Senha para acesso do morador", example = "senha456")
        String senha,

        @Schema(description = "Role do usuário. Deve ser sempre ROLE_MORADOR", example = "ROLE_MORADOR")
        @NotNull(message = "O papel (role) é obrigatório")
        Role role
) {
}
