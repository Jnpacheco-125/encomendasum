package com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta da encomenda registrada")
public record EncomendaResponseDTO(
        @Schema(description = "ID único da encomenda")
        Long id,

        @Schema(description = "Nome do destinatário da encomenda")
        String nomeDestinatario,

        @Schema(description = "Número do apartamento do destinatário")
        String apartamento,

        @Schema(description = "Descrição da encomenda")
        String descricao,

        @Schema(description = "Data de recebimento da encomenda")
        LocalDateTime dataRecebimento,

        @Schema(description = "Indica se a encomenda foi retirada")
        Boolean retirada,

        @Schema(description = "Data de retirada da encomenda")
        LocalDateTime dataRetirada,

        @Schema(description = "Funcionário que recebeu a encomenda")
        FuncionarioResponseDTO funcionarioRecebimento,

        @Schema(description = "Morador destinatário da encomenda")
        MoradorResponseDTO moradorDestinatario
) {
}
