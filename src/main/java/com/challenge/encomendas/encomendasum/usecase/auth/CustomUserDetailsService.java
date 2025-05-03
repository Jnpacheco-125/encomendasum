package com.challenge.encomendas.encomendasum.usecase.auth;

import com.challenge.encomendas.encomendasum.adapters.gateways.FuncionarioGateway;
import com.challenge.encomendas.encomendasum.adapters.gateways.MoradorGateway;
import com.challenge.encomendas.encomendasum.domain.entities.Funcionario;
import com.challenge.encomendas.encomendasum.domain.entities.Morador;
import com.challenge.encomendas.encomendasum.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final FuncionarioGateway funcionarioGateway;
    private final MoradorGateway moradorGateway;

    public CustomUserDetailsService(FuncionarioGateway funcionarioGateway,
                                    MoradorGateway moradorGateway) {
        this.funcionarioGateway = funcionarioGateway;
        this.moradorGateway = moradorGateway;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Verificar se o funcionário existe
        Funcionario funcionario = funcionarioGateway.findByEmail(email).orElse(null);
        // Verificar se o morador existe
        Morador morador = moradorGateway.findByEmail(email).orElse(null);

        if (funcionario == null && morador == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
        }

        // Lista de autoridades (roles) do usuário
        Set<Role> roles;
        String senha;

        if (funcionario != null) {
            roles = funcionario.getRoles();  // Set<Role>
            senha = funcionario.getSenha();
        } else {
            roles = morador.getRoles();      // Set<Role>
            senha = morador.getSenha();
        }

        // Converte o Set<Role> em uma lista de GrantedAuthority
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        // Retorna o UserDetails
        return new User(email, senha, authorities);
    }
}
