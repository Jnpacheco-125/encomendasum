package com.challenge.encomendas.encomendasum.infrastructure.persistence.entities;

import com.challenge.encomendas.encomendasum.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "morador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoradorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String apartamento;
    @Column(unique = true)
    private String email;
    private String senha;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "morador_roles", joinColumns = @JoinColumn(name = "morador_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();
}
