package com.challenge.encomendas.encomendasum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
		"com.challenge.encomendas.encomendasum.infrastructure.persistence.repositories"
})
@EntityScan(basePackages = {
		"com.challenge.encomendas.encomendasum.infrastructure.persistence.entities"
})
@ComponentScan(basePackages = {
		"com.challenge.encomendas.encomendasum" // Garante que serviços, filters, gateways, etc. sejam escaneados
})
public class EncomendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncomendasApplication.class, args);
	}

}
