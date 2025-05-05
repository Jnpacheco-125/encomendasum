package com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Representação do pedido de encomenda")
public record EncomendaRequestDTO(
        @Schema(description = "Nome do destinatário da encomenda")
        @NotBlank(message = "O nome do destinatário é obrigatório")
        String nomeDestinatario,

        @Schema(description = "Número do apartamento do destinatário")
        @NotBlank(message = "O apartamento é obrigatório")
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
        @NotNull(message = "O funcionário responsável pelo recebimento é obrigatório")
        FuncionarioResponseDTO funcionarioRecebimento,

        @Schema(description = "Morador destinatário da encomenda")
        @NotNull(message = "O morador destinatário é obrigatório")
        MoradorResponseDTO moradorDestinatario
) {
}
