package com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario;

import com.challenge.encomendas.encomendasum.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para cadastrar um novo funcionário")
public record CadastroFuncionarioDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        @Schema(description = "Nome completo do funcionário", example = "Ana Souza")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "Endereço de e-mail do funcionário", example = "ana.souza@email.com", format = "email")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        @Schema(description = "Senha para acesso do funcionário", example = "senha123")
        String senha,

        @Schema(description = "Tipo de funcionário: ROLE_ADMIN ou ROLE_PORTEIRO", example = "ROLE_ADMIN")
        @NotNull(message = "O papel (role) é obrigatório")
        Role role
) {
}