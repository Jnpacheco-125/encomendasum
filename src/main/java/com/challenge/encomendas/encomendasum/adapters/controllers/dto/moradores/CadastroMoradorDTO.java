package com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores;

import com.challenge.encomendas.encomendasum.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroMoradorDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "O telefone é obrigatório")
        @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres")
        String telefone,

        @NotBlank(message = "O apartamento é obrigatório")
        @Size(max = 10, message = "O apartamento não pode ter mais de 10 caracteres")
        String apartamento,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String senha,

        @NotNull(message = "O papel (role) é obrigatório")
        Role role
) {
}
