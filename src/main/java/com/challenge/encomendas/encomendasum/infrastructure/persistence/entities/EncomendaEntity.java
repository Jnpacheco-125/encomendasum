package com.challenge.encomendas.encomendasum.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "encomenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncomendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nomeDestinatario;
    private String apartamento;
    private String descricao;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataRecebimento;
    @Column(nullable = false)
    private Boolean retirada;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataRetirada;

    // Associação opcional com Funcionario e Morador (se quiser salvar IDs relacionados)
    @ManyToOne
    @JoinColumn(name = "funcionario_recebimento_id")
    private FuncionarioEntity funcionarioRecebimento;

    @ManyToOne
    @JoinColumn(name = "morador_destinatario_id")
    private MoradorEntity moradorDestinatario;
}
