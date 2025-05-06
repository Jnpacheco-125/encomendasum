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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EncomendaService {
    private final EncomendaGateway encomendaGateway;
    private final FuncionarioGateway funcionarioGateway;
    private final MoradorGateway moradorGateway;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EncomendaService(EncomendaGateway encomendaGateway,
                            FuncionarioGateway funcionarioGateway,
                            MoradorGateway moradorGateway,
                            PasswordEncoder passwordEncoder,
                            JavaMailSender javaMailSender) {
        this.encomendaGateway = encomendaGateway;
        this.funcionarioGateway = funcionarioGateway;
        this.moradorGateway = moradorGateway;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }


    public Encomenda cadastrar(EncomendaRequestDTO dto) {
        Encomenda novaEncomenda = new Encomenda();
        novaEncomenda.setNomeDestinatario(dto.nomeDestinatario());
        novaEncomenda.setApartamento(dto.apartamento());
        novaEncomenda.setDescricao(dto.descricao());
        novaEncomenda.setDataRecebimento(dto.dataRecebimento());
        novaEncomenda.setRetirada(dto.retirada());
        novaEncomenda.setDataRetirada(dto.dataRetirada());

        // Buscar funcionário e morador pelo banco de dados utilizando seus IDs
        FuncionarioResponseDTO funcionarioDTO = dto.funcionarioRecebimento();
        Funcionario funcionarioRecebimento = funcionarioGateway.findById(funcionarioDTO.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        MoradorResponseDTO moradorDTO = dto.moradorDestinatario();
        Morador moradorDestinatario = moradorGateway.findById(moradorDTO.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));

        novaEncomenda.setFuncionarioRecebimento(funcionarioRecebimento);
        novaEncomenda.setMoradorDestinatario(moradorDestinatario);

        Encomenda encomendaSalva = encomendaGateway.save(novaEncomenda);

        if (moradorDestinatario != null) {
            enviarEmail(moradorDestinatario);
        } else {
            System.err.println("Erro: Morador não foi carregado corretamente!");
        }
        return encomendaSalva;
    }
    private void enviarEmail(Morador moradorDestinatario) {
        if (moradorDestinatario != null && moradorDestinatario.getEmail() != null) {
            var message = new SimpleMailMessage();
            message.setFrom("noreply@email.com");
            message.setTo(moradorDestinatario.getEmail());
            message.setSubject("Você tem encomenda para recebida na portaria");
            message.setText("Você tem encomenda para receber na portaria");

            try {
                javaMailSender.send(message);
                System.out.println("E-mail enviado com sucesso para: " + moradorDestinatario.getEmail());
            } catch (MailException e) {
                System.err.println("Erro ao enviar e-mail: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Erro: Dados do morador inválidos!");
        }
    }

    public Encomenda buscarPorId(Long id) {
        return encomendaGateway.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Encomenda não encontrada."));
    }

    public List<Encomenda> buscarEncomendasPendentes() {
        return encomendaGateway.findAllByRetiradaFalse(); // Removendo .map(EncomendaMapper::toDomain)
    }

    public List<Encomenda> buscarEncomendasPorMorador(Long moradorId) {
        return encomendaGateway.findByMoradorDestinatarioId(moradorId);
    }

    public Encomenda confirmarRetirada(Long id, AtualizarEncomendaDTO dto) {
        Encomenda encomenda = encomendaGateway.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Encomenda não encontrada."));

        if (encomenda.getRetirada()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Encomenda já foi retirada.");
        }

        // Atualizando a encomenda com os dados do DTO
        encomenda.setRetirada(dto.retirada());
        encomenda.setDataRetirada(LocalDateTime.now());

        Encomenda atualizada = encomendaGateway.save(encomenda);

        if (atualizada.getMoradorDestinatario() != null) {
            System.out.println("Email do morador: " + atualizada.getMoradorDestinatario().getEmail());
            enviarConfirmacaoEmail(atualizada.getMoradorDestinatario());
        } else {
            System.err.println("Erro: Morador não foi carregado corretamente!");
        }

        return atualizada;
    }

    private void enviarConfirmacaoEmail(Morador morador) {
        if (morador != null && morador.getEmail() != null) {
            // Validação do formato do e-mail
            if (!morador.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.err.println("E-mail inválido: " + morador.getEmail());
                return;
            }

            System.out.println(morador);

            var message = new SimpleMailMessage();
            message.setFrom("noreply@email.com");
            message.setTo(morador.getEmail());
            message.setSubject("Encomenda retirada com sucesso");
            message.setText("Confirmamos que sua encomenda foi retirada na portaria.");

            try {
                javaMailSender.send(message);
                System.out.println("E-mail de confirmação enviado para: " + morador.getEmail());
            } catch (MailException e) {
                System.err.println("Erro ao enviar e-mail de confirmação: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Dados de morador inválidos para envio de e-mail.");
        }
    }

    public Page<Encomenda> buscarEncomendasRetiradas(Pageable pageable) {
        return encomendaGateway.findAllByRetiradaTrue(pageable);
    }


}
