package com.challenge.encomendas.encomendasum.usecase;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.AtualizarFuncionarioDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.CadastroFuncionarioDTO;
import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.domain.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {
    @Mock
    private FuncionarioGateway funcionarioGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Test
    public void deveCadastrarFuncionarioComSucesso() {
        CadastroFuncionarioDTO dto = new CadastroFuncionarioDTO("João", "joao@email.com", "12345", Role.ROLE_ADMIN);
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.nome());
        funcionario.setEmail(dto.email());
        funcionario.setSenha("senhaCodificada");
        funcionario.adicionarRole(dto.role());

        Mockito.when(passwordEncoder.encode(dto.senha())).thenReturn("senhaCodificada");
        Mockito.when(funcionarioGateway.save(Mockito.any(Funcionario.class))).thenReturn(funcionario);

        Funcionario resultado = funcionarioService.cadastrar(dto);

        Assertions.assertEquals("João", resultado.getNome());
        Assertions.assertEquals("joao@email.com", resultado.getEmail());
        Assertions.assertEquals("senhaCodificada", resultado.getSenha());
        Assertions.assertTrue(resultado.getRoles().contains(Role.ROLE_ADMIN));
    }

    @Test
    public void deveBuscarFuncionarioPorId_ComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Carlos");
        funcionario.setEmail("carlos@email.com");

        Mockito.when(funcionarioGateway.findById(1L)).thenReturn(Optional.of(funcionario));

        Funcionario resultado = funcionarioService.buscarPorId(1L);

        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals("Carlos", resultado.getNome());
    }

    @Test
    public void deveLancarExcecao_QuandoFuncionarioNaoExiste() {
        Mockito.when(funcionarioGateway.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            funcionarioService.buscarPorId(99L);
        });
    }

    @Test
    public void deveBuscarFuncionarioPorEmail_ComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria");
        funcionario.setEmail("maria@email.com");

        Mockito.when(funcionarioGateway.findByEmail("maria@email.com")).thenReturn(Optional.of(funcionario));

        Funcionario resultado = funcionarioService.buscarPorEmail("maria@email.com");

        Assertions.assertEquals("Maria", resultado.getNome());
        Assertions.assertEquals("maria@email.com", resultado.getEmail());
    }

    @Test
    public void deveLancarExcecao_QuandoEmailNaoExiste() {
        Mockito.when(funcionarioGateway.findByEmail("naoExiste@email.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            funcionarioService.buscarPorEmail("naoExiste@email.com");
        });
    }

    @Test
    public void deveAtualizarFuncionario_ComSucesso() {
        Funcionario funcionarioExistente = new Funcionario();
        funcionarioExistente.setId(1L);
        funcionarioExistente.setNome("Carlos");
        funcionarioExistente.setEmail("carlos@email.com");

        AtualizarFuncionarioDTO dto = new AtualizarFuncionarioDTO("Carlos Silva", "carlos.silva@email.com");

        Mockito.when(funcionarioGateway.findById(1L)).thenReturn(Optional.of(funcionarioExistente));
        Mockito.when(funcionarioGateway.save(Mockito.any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario resultado = funcionarioService.atualizar(1L, dto);

        Assertions.assertEquals("Carlos Silva", resultado.getNome());
        Assertions.assertEquals("carlos.silva@email.com", resultado.getEmail());
    }

    @Test
    public void deveLancarExcecao_QuandoFuncionarioNaoExisteAtualizar() {
        AtualizarFuncionarioDTO dto = new AtualizarFuncionarioDTO("Novo Nome", "novo@email.com");

        Mockito.when(funcionarioGateway.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            funcionarioService.atualizar(99L, dto);
        });
    }

    @Test
    public void deveDeletarFuncionario_ComSucesso() {
        Long idFuncionario = 1L;

        Mockito.when(funcionarioGateway.findById(idFuncionario)).thenReturn(Optional.of(new Funcionario()));
        Mockito.doNothing().when(funcionarioGateway).deleteById(idFuncionario);

        funcionarioService.deletarFuncionario(idFuncionario);

        Mockito.verify(funcionarioGateway, Mockito.times(1)).deleteById(idFuncionario);
    }

    @Test
    public void deveLancarExcecao_QuandoFuncionarioNaoExisteDelet() {
        Long idInexistente = 99L;

        Mockito.when(funcionarioGateway.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            funcionarioService.deletarFuncionario(idInexistente);
        });
    }

    @Test
    public void deveRetornarListaDeFuncionarios_QuandoExistiremFuncionarios() {
        List<Funcionario> listaFuncionarios = Arrays.asList(
                new Funcionario(1L, "Carlos", "carlos@email.com", "senha123", new HashSet<>()),
                new Funcionario(2L, "Maria", "maria@email.com", "senha123", new HashSet<>())

        );

        Mockito.when(funcionarioGateway.findAll()).thenReturn(listaFuncionarios);

        List<Funcionario> resultado = funcionarioService.buscarTodos();

        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Carlos", resultado.get(0).getNome());
        Assertions.assertEquals("Maria", resultado.get(1).getNome());
    }

    @Test
    public void deveRetornarListaVazia_QuandoNaoExistiremFuncionarios() {
        Mockito.when(funcionarioGateway.findAll()).thenReturn(Collections.emptyList());

        List<Funcionario> resultado = funcionarioService.buscarTodos();

        Assertions.assertTrue(resultado.isEmpty());
    }

}
