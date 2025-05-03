package com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores;

public record MoradorResponseDTO(

    Long id,

    String nome,

    String telefone,

    String apartamento,

    String email
) {
}
