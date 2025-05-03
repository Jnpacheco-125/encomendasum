package com.challenge.encomendas.encomendasum.domain.entities;



import com.challenge.encomendas.encomendasum.domain.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class Funcionario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Set<Role> roles = new HashSet<>();

    // Construtores
    public Funcionario() {
    }
    public Funcionario(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Funcionario(Long id, String nome, String email, String senha, Set<Role> roles) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.roles = roles != null ? roles : new HashSet<>(); // Evita null pointer caso roles seja null
    }


    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    // Método auxiliar para adicionar uma role
    public void adicionarRole(Role role) {
        this.roles.add(role);
    }
}
