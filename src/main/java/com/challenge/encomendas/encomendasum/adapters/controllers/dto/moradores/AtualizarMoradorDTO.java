package com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AtualizarMoradorDTO(

        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,


        @Pattern(regexp = "^[0-9]+$", message = "O telefone deve conter apenas números")
        String telefone,


        String apartamento,

        @Email(message = "Formato de e-mail inválido")
        String email
) {
}
