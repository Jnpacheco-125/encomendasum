package com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AtualizarFuncionarioDTO(
        String nome,
        @Email(message = "Formato de e-mail inválido")
        String email
) {
}
