package com.challenge.encomendas.encomendasum.usecase;

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
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

}
