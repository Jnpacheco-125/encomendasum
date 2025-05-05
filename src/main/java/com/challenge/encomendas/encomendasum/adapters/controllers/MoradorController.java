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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/moradores")
@Tag(name = "Moradores", description = "Operações relacionadas ao gerenciamento de moradores")
public class MoradorController {
    private final MoradorService moradorService;
    private final AuthService authService;

    public MoradorController(MoradorService moradorService, AuthService authService) {
        this.moradorService = moradorService;
        this.authService = authService;
    }

    @Operation(summary = "Login de morador", description = "Autentica um morador e retorna um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
    })
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

    @Operation(
            summary = "Cadastro de morador",
            description = "Registra um novo morador no sistema.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de cadastro de morador",
                                            value = """
                                {
                                  "nome": "Clara Residente",
                                  "email": "clara@residencial.com",
                                  "senha": "minhasenha123",
                                  "telefone": "11987654321",
                                  "apartamento": "101",
                                  "role": "ROLE_MORADOR"
                                }
                                """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Morador cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoradorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou morador já existente", content = @Content)
    })
    @PostMapping("/cadastro")
    public ResponseEntity<MoradorResponseDTO> cadastrarMorador(@Valid @RequestBody CadastroMoradorDTO dto) {
        Morador novoMorador = moradorService.cadastrar(dto);
        MoradorResponseDTO response = MoradorMapper.toResponseDTO(novoMorador);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Buscar morador por ID",
            description = "Retorna os detalhes de um morador específico com base no seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Morador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoradorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorId(@PathVariable Long id) {
        Morador morador = moradorService.buscarPorId(id);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Buscar morador por e-mail",
            description = "Retorna os detalhes de um morador com base no seu e-mail."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Morador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoradorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar-por-email")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorEmail(@RequestParam String email) {
        Morador morador = moradorService.buscarPorEmail(email);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Buscar morador por telefone",
            description = "Retorna os detalhes de um morador com base no seu telefone."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Morador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoradorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar-por-telefone")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorTelefone(@RequestParam String telefone) {
        Morador morador = moradorService.buscarPorTelefone(telefone);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Buscar morador por apartamento",
            description = "Retorna os detalhes de um morador com base no número do apartamento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Morador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoradorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar-por-apartamento")
    public ResponseEntity<MoradorResponseDTO> buscarMoradorPorApartamento(@RequestParam String apartamento) {
        Morador morador = moradorService.buscarPorApartamento(apartamento);
        MoradorResponseDTO responseDTO = MoradorMapper.toResponseDTO(morador);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Listar todos os moradores",
            description = "Retorna uma lista com todos os moradores cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de moradores encontrada",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MoradorResponseDTO.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum morador encontrado", content = @Content)
    })
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

    @Operation(
            summary = "Atualizar morador",
            description = "Atualiza os dados de um morador existente no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Morador atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoradorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @SecurityRequirement(name = "Bearer Auth")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MoradorResponseDTO> atualizarMorador(@PathVariable Long id,
                                                               @Valid @RequestBody AtualizarMoradorDTO dto) {
        Morador moradorAtualizado = moradorService.atualizar(id, dto);
        MoradorResponseDTO response = MoradorMapper.toResponseDTO(moradorAtualizado);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar morador por ID", description = "Deleta um morador do sistema com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Morador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Morador não encontrado")
    })
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