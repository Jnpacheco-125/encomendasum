package com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EncomendaRequestDTO(

        @NotBlank(message = "O nome do destinatário é obrigatório")
        String nomeDestinatario,


        @NotBlank(message = "O apartamento é obrigatório")
        String apartamento,


        String descricao,


        LocalDateTime dataRecebimento,


        Boolean retirada,


        LocalDateTime dataRetirada,


        @NotNull(message = "O funcionário responsável pelo recebimento é obrigatório")
        FuncionarioResponseDTO funcionarioRecebimento,


        @NotNull(message = "O morador destinatário é obrigatório")
        MoradorResponseDTO moradorDestinatario
) {
}
