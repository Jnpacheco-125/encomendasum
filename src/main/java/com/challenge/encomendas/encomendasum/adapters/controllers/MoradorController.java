package com.challenge.encomendas.encomendasum.adapters.controllers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.CadastroMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.MoradorMapper;
import com.challenge.encomendas.encomendasum.usecase.MoradorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {
    private final MoradorService moradorService;

    public MoradorController(MoradorService moradorService) {
        this.moradorService = moradorService;

    }

    @PostMapping("/cadastro")
    public ResponseEntity<MoradorResponseDTO> cadastrarMorador(@Valid @RequestBody CadastroMoradorDTO dto) {
        Morador novoMorador = moradorService.cadastrar(dto);
        MoradorResponseDTO response = MoradorMapper.toResponseDTO(novoMorador);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
