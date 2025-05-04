package com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record EncomendaResponseDTO(

        Long id,


        String nomeDestinatario,


        String apartamento,


        String descricao,


        LocalDateTime dataRecebimento,


        Boolean retirada,


        LocalDateTime dataRetirada,


        FuncionarioResponseDTO funcionarioRecebimento,


        MoradorResponseDTO moradorDestinatario
) {
}
