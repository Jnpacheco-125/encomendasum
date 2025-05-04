package com.challenge.encomendas.encomendasum.adapters.controllers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.login.LoginRequestDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.login.LoginResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.AtualizarMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.CadastroMoradorDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.MoradorMapper;
import com.challenge.encomendas.encomendasum.usecase.MoradorService;
import com.challenge.encomendas.encomendasum.usecase.auth.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorId(@PathVariable Long id) {
        Morador morador = moradorService.buscarPorId(id);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar-por-email")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorEmail(@RequestParam String email) {
        Morador morador = moradorService.buscarPorEmail(email);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar-por-telefone")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorTelefone(@RequestParam String telefone) {
        Morador morador = moradorService.buscarPorTelefone(telefone);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar-por-apartamento")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorApartamento(@RequestParam String apartamento) {
        Morador morador = moradorService.buscarPorApartamento(apartamento);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<MoradorResponseDTO>> listarTodosMoradores() {
        List<Morador> moradores = moradorService.buscarTodos();

        if (moradores.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 se não tiver moradores
        }

        List<MoradorResponseDTO> response = moradores.stream()
                .map(MoradorMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MoradorResponseDTO> atualizarMorador(@PathVariable Long id,
                                                               @Valid @RequestBody AtualizarMoradorDTO dto) {
        Morador moradorAtualizado = moradorService.atualizar(id, dto);
        MoradorResponseDTO response = MoradorMapper.toResponseDTO(moradorAtualizado);
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/moradores/{id}")
    public ResponseEntity<Void> deletarMorador(@PathVariable Long id) {
        try {
            moradorService.deletarMorador(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build(); // 404 ou outro
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 erro inesperado
        }
    }
}