package com.challenge.encomendas.encomendasum.adapters.controllers;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.CadastroFuncionarioDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.login.LoginRequestDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.login.LoginResponseDTO;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.infrastructure.persistence.mappers.FuncionarioMapper;
import com.challenge.encomendas.encomendasum.usecase.FuncionarioService;
import com.challenge.encomendas.encomendasum.usecase.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    private final AuthService authService;
    private final FuncionarioService funcionarioService;

    public FuncionarioController(AuthService authService, FuncionarioService funcionarioService) {
        this.authService = authService;
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            // Use os métodos `email()` e `senha()` do record
            String token = authService.autenticar(request.email(), request.senha());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<FuncionarioResponseDTO> cadastrarFuncionario(@Valid @RequestBody CadastroFuncionarioDTO cadastroDTO) {


        Funcionario novoFuncionario = funcionarioService.cadastrar(cadastroDTO);

        FuncionarioResponseDTO response = FuncionarioMapper.toResponseDTO(novoFuncionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
