package com.challenge.encomendas.encomendasum.adapters.controllers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas.EncomendaRequestDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas.EncomendaResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Encomenda;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.EncomendaMapper;
import com.challenge.encomendas.encomendasum.usecase.EncomendaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/encomendas")
public class EncomendaController {
    private final EncomendaService encomendaService;

    @Autowired
    public EncomendaController(EncomendaService encomendaService) {
        this.encomendaService = encomendaService;
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('PORTEIRO') or hasRole('ADMIN')")
    @PostMapping("/encomendas")
    public ResponseEntity<EncomendaResponseDTO> cadastrarEncomenda(@Valid @RequestBody EncomendaRequestDTO requestDTO) {
        log.info("Cadastro de encomenda solicitado para o morador: {}", requestDTO.moradorDestinatario().nome());

        Encomenda novaEncomenda = encomendaService.cadastrar(requestDTO);
        EncomendaResponseDTO response = EncomendaMapper.toResponseDTO(novaEncomenda);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
