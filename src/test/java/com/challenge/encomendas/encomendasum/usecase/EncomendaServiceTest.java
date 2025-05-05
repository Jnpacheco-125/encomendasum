package com.challenge.encomendas.encomendasum.usecase;

import com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas.AtualizarEncomendaDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.encomendas.EncomendaRequestDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.funcionario.FuncionarioResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.controllers.dto.moradores.MoradorResponseDTO;
import com.challenge.encomendas.encomendasum.adapters.gateways.EncomendaGateway;
import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.adapters.gateways.MoradorGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Encomenda;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class EncomendaServiceTest {

    @Mock
    private EncomendaGateway encomendaGateway;

    @Mock
    private FuncionarioGateway funcionarioGateway;

    @Mock
    private MoradorGateway moradorGateway;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EncomendaService encomendaService;

    @Test
    public void deveCadastrarEncomenda_ComSucesso() {
        EncomendaRequestDTO dto = new EncomendaRequestDTO(
                "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), false, null,
                new FuncionarioResponseDTO(1L, "Carlos", "carlos@email.com"),
                new MoradorResponseDTO(2L, "Maria", "86999775533", "Apt 101", "maria@email.com")
        );

        Funcionario funcionario = new Funcionario(1L, "Carlos", "carlos@email.com", "senha123", new HashSet<>());
        Morador morador = new Morador(2L, "Maria", "86999775533", "Apt 101", "maria@email.com", "senha456", new HashSet<>());
        Encomenda encomenda = new Encomenda();
        encomenda.setNomeDestinatario(dto.nomeDestinatario());
        encomenda.setApartamento(dto.apartamento());
        encomenda.setDescricao(dto.descricao());
        encomenda.setDataRecebimento(dto.dataRecebimento());
        encomenda.setRetirada(dto.retirada());
        encomenda.setFuncionarioRecebimento(funcionario);
        encomenda.setMoradorDestinatario(morador);

        Mockito.when(funcionarioGateway.findById(1L)).thenReturn(Optional.of(funcionario));
        Mockito.when(moradorGateway.findById(2L)).thenReturn(Optional.of(morador));
        Mockito.when(encomendaGateway.save(Mockito.any(Encomenda.class))).thenReturn(encomenda);

        Encomenda resultado = encomendaService.cadastrar(dto);

        Assertions.assertEquals("João da Silva", resultado.getNomeDestinatario());
        Assertions.assertEquals("Apt 101", resultado.getApartamento());
        Assertions.assertEquals("Caixa grande", resultado.getDescricao());
        Assertions.assertEquals(funcionario.getId(), resultado.getFuncionarioRecebimento().getId());
        Assertions.assertEquals(morador.getId(), resultado.getMoradorDestinatario().getId());

        Mockito.verify(javaMailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test
    public void deveLancarExcecao_QuandoFuncionarioNaoExiste() {
        EncomendaRequestDTO dto = new EncomendaRequestDTO(
                "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), false, null,
                new FuncionarioResponseDTO(1L, "Carlos", "carlos@email.com"),
                new MoradorResponseDTO(2L, "Maria", "86999775533", "Apt 101", "maria@email.com")
        );

        Mockito.when(funcionarioGateway.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            encomendaService.cadastrar(dto);
        });
    }

    @Test
    public void deveLancarExcecao_QuandoMoradorNaoExiste() {
        EncomendaRequestDTO dto = new EncomendaRequestDTO(
                "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), false, null,
                new FuncionarioResponseDTO(1L, "Carlos", "carlos@email.com"),
                new MoradorResponseDTO(2L, "Maria", "86999775533", "Apt 101", "maria@email.com")
        );

        Funcionario funcionario = new Funcionario(1L, "Carlos", "carlos@email.com", "senha123", new HashSet<>());

        Mockito.when(funcionarioGateway.findById(1L)).thenReturn(Optional.of(funcionario));
        Mockito.when(moradorGateway.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            encomendaService.cadastrar(dto);
        });
    }

    @Test
    public void deveBuscarEncomendaPorId_ComSucesso() {
        Funcionario funcionarioRecebimento = new Funcionario(1L, "Carlos", "carlos@email.com", "senha123", new HashSet<>());
        Morador moradorDestinatario = new Morador(2L, "Maria", "86999775533", "Apt 101", "maria@email.com", "senha456", new HashSet<>());

        Encomenda encomenda = new Encomenda(1L, "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), false, null, funcionarioRecebimento, moradorDestinatario);



        Mockito.when(encomendaGateway.findById(1L)).thenReturn(Optional.of(encomenda));

        Encomenda resultado = encomendaService.buscarPorId(1L);

        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals("João da Silva", resultado.getNomeDestinatario());
        Assertions.assertEquals("Apt 101", resultado.getApartamento());
        Assertions.assertEquals("Caixa grande", resultado.getDescricao());
    }

    @Test
    public void deveLancarExcecao_QuandoEncomendaNaoExiste() {
        Mockito.when(encomendaGateway.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            encomendaService.buscarPorId(99L);
        });
    }

    @Test
    public void deveRetornarEncomendasPendentes_QuandoExistirem() {
        List<Encomenda> encomendasPendentes = Arrays.asList(
                new Encomenda(1L, "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), false, null, null, null),
                new Encomenda(2L, "Maria Souza", "Apt 202", "Envelope", LocalDateTime.now(), false, null, null, null)
        );

        Mockito.when(encomendaGateway.findAllByRetiradaFalse()).thenReturn(encomendasPendentes);

        List<Encomenda> resultado = encomendaService.buscarEncomendasPendentes();

        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertFalse(resultado.get(0).getRetirada());
        Assertions.assertFalse(resultado.get(1).getRetirada());
    }

    @Test
    public void deveRetornarListaVazia_QuandoNaoExistiremEncomendasPendentes() {
        Mockito.when(encomendaGateway.findAllByRetiradaFalse()).thenReturn(Collections.emptyList());

        List<Encomenda> resultado = encomendaService.buscarEncomendasPendentes();

        Assertions.assertTrue(resultado.isEmpty());
    }
    @Test
    public void deveRetornarEncomendasRetiradas_QuandoExistirem() {
        List<Encomenda> encomendasRetiradas = Arrays.asList(
                new Encomenda(3L, "Pedro Lima", "Apt 303", "Pacote pequeno", LocalDateTime.now(), true, LocalDateTime.now(), null, null),
                new Encomenda(4L, "Ana Oliveira", "Apt 404", "Caixa média", LocalDateTime.now(), true, LocalDateTime.now(), null, null)
        );

        Mockito.when(encomendaGateway.findAllByRetiradaTrue()).thenReturn(encomendasRetiradas);

        List<Encomenda> resultado = encomendaService.buscarEncomendasRetiradas();

        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.get(0).getRetirada());
        Assertions.assertTrue(resultado.get(1).getRetirada());
    }

    @Test
    public void deveRetornarListaVazia_QuandoNaoExistiremEncomendasRetiradas() {
        Mockito.when(encomendaGateway.findAllByRetiradaTrue()).thenReturn(Collections.emptyList());

        List<Encomenda> resultado = encomendaService.buscarEncomendasRetiradas();

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarEncomendasPorMorador_QuandoExistirem() {
        Long moradorId = 2L;
        List<Encomenda> encomendasPorMorador = Arrays.asList(
                new Encomenda(5L, "Maria Souza", "Apt 202", "Envelope", LocalDateTime.now(), false, null, null, null),
                new Encomenda(6L, "Maria Souza", "Apt 202", "Caixa pequena", LocalDateTime.now(), false, null, null, null)
        );

        Mockito.when(encomendaGateway.findByMoradorDestinatarioId(moradorId)).thenReturn(encomendasPorMorador);

        List<Encomenda> resultado = encomendaService.buscarEncomendasPorMorador(moradorId);

        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Maria Souza", resultado.get(0).getNomeDestinatario());
    }

    @Test
    public void deveRetornarListaVazia_QuandoNaoExistiremEncomendasPorMorador() {
        Long moradorId = 99L;
        Mockito.when(encomendaGateway.findByMoradorDestinatarioId(moradorId)).thenReturn(Collections.emptyList());

        List<Encomenda> resultado = encomendaService.buscarEncomendasPorMorador(moradorId);

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveConfirmarRetirada_ComSucesso() {
        Encomenda encomenda = new Encomenda(1L, "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), false, null, null, null);
        AtualizarEncomendaDTO dto = new AtualizarEncomendaDTO(
                null, // Nome do destinatário
                null, // Apartamento
                null, // Descrição
                null, // Data de recebimento
                true, // Retirada
                LocalDateTime.now(), // Data de retirada
                null, // Funcionário que recebeu a encomenda
                null // Morador destinatário
        );


        Morador morador = new Morador(2L, "Maria Souza", "86999775533", "Apt 101", "maria@email.com", "senha123", new HashSet<>());
        encomenda.setMoradorDestinatario(morador);

        Mockito.when(encomendaGateway.findById(1L)).thenReturn(Optional.of(encomenda));
        Mockito.when(encomendaGateway.save(Mockito.any(Encomenda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Encomenda resultado = encomendaService.confirmarRetirada(1L, dto);

        Assertions.assertTrue(resultado.getRetirada());
        Assertions.assertNotNull(resultado.getDataRetirada());

        Mockito.verify(javaMailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test
    public void deveLancarExcecao_QuandoEncomendaJaFoiRetiradaRetirada() {
        Encomenda encomenda = new Encomenda(1L, "João da Silva", "Apt 101", "Caixa grande", LocalDateTime.now(), true, LocalDateTime.now(), null, null);
        AtualizarEncomendaDTO dto = new AtualizarEncomendaDTO(
                null, // Nome do destinatário
                null, // Apartamento
                null, // Descrição
                null, // Data de recebimento
                true, // Retirada
                LocalDateTime.now(), // Data de retirada
                null, // Funcionário que recebeu a encomenda
                null // Morador destinatário
        );


        Mockito.when(encomendaGateway.findById(1L)).thenReturn(Optional.of(encomenda));

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            encomendaService.confirmarRetirada(1L, dto);
        });
    }

    @Test
    public void deveLancarExcecao_QuandoEncomendaRetiradaNaoExiste() {
        AtualizarEncomendaDTO dto = new AtualizarEncomendaDTO(
                null, // Nome do destinatário
                null, // Apartamento
                null, // Descrição
                null, // Data de recebimento
                true, // Retirada
                LocalDateTime.now(), // Data de retirada
                null, // Funcionário que recebeu a encomenda
                null // Morador destinatário
        );


        Mockito.when(encomendaGateway.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            encomendaService.confirmarRetirada(99L, dto);
        });
    }
}
