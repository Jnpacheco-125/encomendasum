package com.challenge.encomendas.encomendasum.adapters.controllers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.login.LoginRequestDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.login.LoginResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.CadastroMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.MoradorMapper;
import com.challenge.encomendas.encomendasum.usecase.MoradorService;
import com.challenge.encomendas.encomendasum.usecase.auth.AuthService;
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
    private final AuthService authService;

    public MoradorController(MoradorService moradorService, AuthService authService) {
        this.moradorService = moradorService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginMorador(@Valid @RequestBody LoginRequestDTO request) {
        try {
            // Chama o authService para autenticar o morador e obter o token JWT
            String token = authService.autenticar(request.email(), request.senha());

            // Retorna o token JWT encapsulado no DTO de resposta
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            // Em caso de erro, retorna resposta com status de "Não autorizado"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<MoradorResponseDTO> cadastrarMorador(@Valid @RequestBody CadastroMoradorDTO dto) {
        Morador novoMorador = moradorService.cadastrar(dto);
        MoradorResponseDTO response = MoradorMapper.toResponseDTO(novoMorador);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
