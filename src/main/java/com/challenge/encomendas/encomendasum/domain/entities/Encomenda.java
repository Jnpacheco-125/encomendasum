package com.challenge.encomendas.encomendasum.domain.entities;

import java.time.LocalDateTime;

public class Encomenda {
    private Long id;
    private String nomeDestinatario;
    private String apartamento;
    private String descricao;
    private LocalDateTime dataRecebimento;
    private Boolean retirada;
    private LocalDateTime dataRetirada;
    private Funcionario funcionarioRecebimento;
    private Morador moradorDestinatario;

    public Encomenda() {
    }
    public Encomenda(Long id, String nomeDestinatario, String apartamento, String descricao,
                     LocalDateTime dataRecebimento, Boolean retirada, LocalDateTime dataRetirada,
                     Funcionario funcionarioRecebimento, Morador moradorDestinatario) {
        this.id = id;
        this.nomeDestinatario = nomeDestinatario;
        this.apartamento = apartamento;
        this.descricao = descricao;
        this.dataRecebimento = dataRecebimento;
        this.retirada = retirada != null ? retirada : false;
        this.dataRetirada = dataRetirada;
        this.funcionarioRecebimento = funcionarioRecebimento;
        this.moradorDestinatario = moradorDestinatario;
    }

    // Getters
    public Long getId() { return id; }
    public String getNomeDestinatario() { return nomeDestinatario; }
    public String getApartamento() { return apartamento; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getDataRecebimento() { return dataRecebimento; }
    public Boolean getRetirada() { return retirada; }
    public LocalDateTime getDataRetirada() { return dataRetirada; }
    public Funcionario getFuncionarioRecebimento() { return funcionarioRecebimento; }
    public Morador getMoradorDestinatario() { return moradorDestinatario; }

    // Setters para atributos que podem mudar
    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public void setRetirada(Boolean retirada) {
        this.retirada = retirada;
    }

    public void setDataRetirada(LocalDateTime dataRetirada) {
        this.dataRetirada = dataRetirada;
    }
    public void setFuncionarioRecebimento(Funcionario funcionarioRecebimento) {
        this.funcionarioRecebimento = funcionarioRecebimento;
    }

    public void setMoradorDestinatario(Morador moradorDestinatario) {
        this.moradorDestinatario = moradorDestinatario;
    }
}
